package com.cjq.htty.abs.impl;

import com.cjq.htty.HandlerInfo;
import com.cjq.htty.abs.*;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Wrap HttpResponder to call post handler hook.
 */
@Slf4j
final class WrappedHttpResponder extends AbstractHttpResponder {

  private final HttpResponder delegate;
  private final Iterable<? extends HandlerHook> handlerHooks;
  private final HttpRequest httpRequest;
  private final HandlerInfo handlerInfo;

  WrappedHttpResponder(HttpResponder delegate, Iterable<? extends HandlerHook> handlerHooks,
                       HttpRequest httpRequest, HandlerInfo handlerInfo) {
    this.delegate = delegate;
    this.handlerHooks = handlerHooks;
    this.httpRequest = httpRequest;
    this.handlerInfo = handlerInfo;
  }

  @Override
  public ChunkResponder sendChunkStart(final HttpResponseStatus status, HttpHeaders headers) {
    final ChunkResponder chunkResponder = delegate.sendChunkStart(status, headers);
    return new ChunkResponder() {
      @Override
      public void sendChunk(ByteBuffer chunk) throws IOException {
        chunkResponder.sendChunk(chunk);
      }

      @Override
      public void sendChunk(ByteBuf chunk) throws IOException {
        chunkResponder.sendChunk(chunk);
      }

      @Override
      public void close() throws IOException {
        chunkResponder.close();
        runHook(status);
      }
    };
  }

  @Override
  public void sendContent(HttpResponseStatus status, ByteBuf content, HttpHeaders headers) {
    delegate.sendContent(status, content, headers);
    runHook(status);
  }

  @Override
  public void sendFile(File file, HttpHeaders headers) throws IOException {
    delegate.sendFile(file, headers);
    runHook(HttpResponseStatus.OK);
  }

  @Override
  public void sendContent(HttpResponseStatus status, BodyProducer bodyProducer, HttpHeaders headers) {
    delegate.sendContent(status, bodyProducer, headers);
    runHook(status);
  }

  private void runHook(HttpResponseStatus status) {
    for (HandlerHook hook : handlerHooks) {
      try {
        hook.postCall(httpRequest, status, handlerInfo);
      } catch (Throwable t) {
        log.error("Post handler hook threw exception: ", t);
      }
    }
  }
}
