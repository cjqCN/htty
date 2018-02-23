package com.cjq.htty;

import com.cjq.htty.abs.HttpHandler;

/**
 * Contains information about {@link HttpHandler} method.
 */
public class HandlerInfo {
  private final String handlerName;
  private final String methodName;

  public HandlerInfo(String handlerName, String methodName) {
    this.handlerName = handlerName;
    this.methodName = methodName;
  }

  public String getHandlerName() {
    return handlerName;
  }

  public String getMethodName() {
    return methodName;
  }
}
