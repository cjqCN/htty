package com.cjq.htty.abs;

import io.netty.buffer.ByteBuf;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A responder for sending chunk-encoded response
 */
public interface ChunkResponder extends Closeable {

  /**
   * Adds a chunk of data to the response. The content will be sent to the client asynchronously.
   *
   * @param chunk content to send
   * @throws IOException if the connection is already closed
   */
  void sendChunk(ByteBuffer chunk) throws IOException;

  /**
   * Adds a chunk of data to the response. The content will be sent to the client asynchronously.
   *
   * @param chunk content to send
   * @throws IOException if this {@link ChunkResponder} already closed or the connection is closed
   */
  void sendChunk(ByteBuf chunk) throws IOException;

  /**
   * Closes this responder which signals the end of the chunk response.
   */
  @Override
  void close() throws IOException;
}
