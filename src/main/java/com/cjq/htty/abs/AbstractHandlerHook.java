package com.cjq.htty.abs;

import com.cjq.htty.HandlerInfo;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * A base implementation of {@link HandlerHook} that provides no-op for both
 * {@link HandlerHook#preCall(HttpRequest, HttpResponder, HandlerInfo)}
 * and {@link HandlerHook#postCall(HttpRequest, HttpResponseStatus, HandlerInfo)} methods.
 */
public abstract class AbstractHandlerHook implements HandlerHook {
  @Override
  public boolean preCall(HttpRequest request, HttpResponder responder, HandlerInfo handlerInfo) {
    return true;
  }

  @Override
  public void postCall(HttpRequest request, HttpResponseStatus status, HandlerInfo handlerInfo) {
    // no-op
  }
}
