package com.cjq.htty;

import com.cjq.htty.core.AbstractHttpResponder;
import com.cjq.htty.core.BodyProducer;
import com.cjq.htty.core.ChunkResponder;
import com.cjq.htty.core.HttpResponder;
import com.cjq.htty.util.HttpUtils;
import com.sun.istack.internal.Nullable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Basic implementation of {@link HttpResponder} that uses {@link Channel} to write back to client.
 */
public final class BasicHttpResponder extends AbstractHttpResponder {

  private static final Logger LOG = LoggerFactory.getLogger(BasicHttpResponder.class);

  private final Channel channel;
  private final AtomicBoolean responded;
  private final boolean sslEnabled;

  public BasicHttpResponder(Channel channel, boolean sslEnabled) {
    this.channel = channel;
    this.responded = new AtomicBoolean(false);
    this.sslEnabled = sslEnabled;
  }

  @Override
  public ChunkResponder sendChunkStart(HttpResponseStatus status, HttpHeaders headers) {
    if (status.code() < 200 || status.code() >= 210) {
      throw new IllegalArgumentException("Status code must be between 200 and 210. Status code provided is "
              + status.code());
    }

    HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
    addContentTypeIfMissing(response.headers().add(headers), OCTET_STREAM_TYPE);

    if (HttpUtils.getContentLength(response, -1L) < 0) {
      HttpUtils.setTransferEncodingChunked(response, true);
    }

    checkNotResponded();
    channel.write(response);
    return new ChannelChunkResponder(channel);
  }

  @Override
  public void sendContent(HttpResponseStatus status, ByteBuf content, HttpHeaders headers) {
    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
    response.headers().add(headers);
    HttpUtils.setContentLength(response, content.readableBytes());

    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

    if (content.isReadable()) {
      addContentTypeIfMissing(response.headers(), OCTET_STREAM_TYPE);
    }

    checkNotResponded();
    channel.writeAndFlush(response);
  }

  @Override
  public void sendFile(File file, HttpHeaders headers) throws IOException {
    HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    addContentTypeIfMissing(response.headers().add(headers), OCTET_STREAM_TYPE);

    HttpUtils.setTransferEncodingChunked(response, false);
    HttpUtils.setContentLength(response, file.length());

    // Open the file first to make sure it is readable before sending out the response
    RandomAccessFile raf = new RandomAccessFile(file, "r");
    try {
      checkNotResponded();

      // Write the initial line and the header.
      channel.write(response);

      // Write the content.
      // FileRegion only works in non-SSL case. For SSL case, use ChunkedFile instead.
      // See https://github.com/netty/netty/issues/2075
      if (sslEnabled) {
        // The HttpChunkedInput will write out the last content
        channel.writeAndFlush(new HttpChunkedInput(new ChunkedFile(raf, 8192)));
      } else {
        // The FileRegion will close the file channel when it is done sending.
        FileRegion region = new DefaultFileRegion(raf.getChannel(), 0, file.length());
        channel.write(region);
        channel.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
      }
    } catch (Throwable t) {
      try {
        raf.close();
      } catch (IOException ex) {
        t.addSuppressed(ex);
      }
      throw t;
    }
  }

  @Override
  public void sendContent(HttpResponseStatus status, final BodyProducer bodyProducer, HttpHeaders headers) {
    final long contentLength;
    try {
      contentLength = bodyProducer.getContentLength();
    } catch (Throwable t) {
      bodyProducer.handleError(t);
      // Response with error and close the connection
      sendContent(
              HttpResponseStatus.INTERNAL_SERVER_ERROR,
              Unpooled.copiedBuffer("Failed to determined content length. Cause: " + t.getMessage(), StandardCharsets.UTF_8),
              new DefaultHttpHeaders()
                      .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE)
                      .set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=utf-8"));
      return;
    }

    HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    addContentTypeIfMissing(response.headers().add(headers), OCTET_STREAM_TYPE);

    if (contentLength < 0L) {
      HttpUtils.setTransferEncodingChunked(response, true);
    } else {
      HttpUtils.setTransferEncodingChunked(response, false);
      HttpUtils.setContentLength(response, contentLength);
    }

    checkNotResponded();

    // Streams the data produced by the given BodyProducer
    channel.writeAndFlush(response).addListener(new ChannelFutureListener() {

      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
          callBodyProducerHandleError(bodyProducer, future.cause());
          channel.close();
          return;
        }
        channel.writeAndFlush(new HttpChunkedInput(new BodyProducerChunkedInput(bodyProducer, contentLength)))
                .addListener(createBodyProducerCompletionListener(bodyProducer));
      }
    });
  }

  /**
   * Returns {@code true} if response was sent.
   */
  boolean isResponded() {
    return responded.get();
  }

  private ChannelFutureListener createBodyProducerCompletionListener(final BodyProducer bodyProducer) {
    return new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
          callBodyProducerHandleError(bodyProducer, future.cause());
          channel.close();
          return;
        }

        try {
          bodyProducer.finished();
        } catch (Throwable t) {
          callBodyProducerHandleError(bodyProducer, t);
          channel.close();
        }
      }
    };
  }

  private void callBodyProducerHandleError(BodyProducer bodyProducer, @Nullable Throwable failureCause) {
    try {
      bodyProducer.handleError(failureCause);
    } catch (Throwable t) {
      LOG.warn("Exception raised from BodyProducer.handleError() for {}", bodyProducer, t);
    }
  }

  private void checkNotResponded() {
    if (!responded.compareAndSet(false, true)) {
      throw new IllegalStateException("Response has already been sent");
    }
  }

  /**
   * A {@link ChunkedInput} implementation that produce chunks using {@link BodyProducer}.
   */
  private static final class BodyProducerChunkedInput implements ChunkedInput<ByteBuf> {

    private final BodyProducer bodyProducer;
    private final long length;
    private long bytesProduced;
    private ByteBuf nextChunk;
    private boolean completed;

    private BodyProducerChunkedInput(BodyProducer bodyProducer, long length) {
      this.bodyProducer = bodyProducer;
      this.length = length;
    }

    @Override
    public boolean isEndOfInput() throws Exception {
      if (completed) {
        return true;
      }
      if (nextChunk == null) {
        nextChunk = bodyProducer.nextChunk();
      }

      completed = !nextChunk.isReadable();
      if (completed && length >= 0 && bytesProduced != length) {
        throw new IllegalStateException("Body size doesn't match with content length. " +
                "Content-Length: " + length + ", bytes produced: " + bytesProduced);
      }

      return completed;
    }

    @Override
    public void close() throws Exception {
      // No-op. Calling of the BodyProducer.finish() is done via the channel future completion.
    }

    @Override
    public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
      return readChunk(ctx.alloc());
    }

    public ByteBuf readChunk(ByteBufAllocator allocator) throws Exception {
      if (isEndOfInput()) {
        // This shouldn't happen, but just to guard
        throw new IllegalStateException("No more data to produce from body producer");
      }
      ByteBuf chunk = nextChunk;
      bytesProduced += chunk.readableBytes();
      nextChunk = null;
      return chunk;
    }

    @Override
    public long length() {
      return length;
    }

    @Override
    public long progress() {
      return length >= 0 ? bytesProduced : 0;
    }
  }
}
