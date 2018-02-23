package com.cjq.htty.abs;


import com.cjq.htty.HttpResourceHandler;
import com.cjq.htty.abs.impl.InternalHttpResponder;
import io.netty.handler.codec.http.HttpRequest;

/**
 * A base implementation of {@link HttpHandler} that provides a method for sending a request to other
 * handlers that exist in the same server.
 */
public abstract class AbstractHttpHandler implements HttpHandler {
  private HttpResourceHandler httpResourceHandler;

  @Override
  public void init(HandlerContext context) {
    this.httpResourceHandler = context.getHttpResourceHandler();
  }

  @Override
  public void destroy(HandlerContext context) {
    // No-op
  }

  /**
   * Send a request to another handler internal to the server, getting back the response body and response code.
   *
   * @param request request to send to another handler.
   * @return {@link InternalHttpResponse} containing the response code and body.
   */
  protected InternalHttpResponse sendInternalRequest(HttpRequest request) {
    InternalHttpResponder responder = new InternalHttpResponder();
    httpResourceHandler.handle(request, responder);
    return responder.getResponse();
  }
}
