package com.cjq.htty.abs;

import io.netty.buffer.ByteBuf;

/**
 * HttpHandler would extend this abstract class and implement methods to stream the body directly.
 * chunk method would receive the http-chunks of the body and finished would be called
 * on receipt of the last chunk.
 */
public abstract class BodyConsumer {
  /**
   * Http request content will be streamed directly to this method.
   *
   * @param request the next chunk to be consumed
   * @param responder a {@link HttpResponder} for sending response back to client.
   */
  public abstract void chunk(ByteBuf request, HttpResponder responder);

  /**
   * This is called on the receipt of the last HttpChunk.
   *
   * @param responder a {@link HttpResponder} for sending response back to client.
   */
  public abstract void finished(HttpResponder responder);

  /**
   * When there is exception on netty while streaming, it will be propagated to handler
   * so the handler can do the cleanup. Implementations should not write to an HttpResponder.
   * Instead, use a {@link ExceptionHandler}.
   *
   * @param cause the reaons of the failure.
   */
  public abstract void handleError(Throwable cause);
}
