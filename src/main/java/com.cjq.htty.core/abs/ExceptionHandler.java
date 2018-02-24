package com.cjq.htty.core.abs;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public abstract class ExceptionHandler {
    public abstract void handle(Throwable t, HttpRequest request, HttpResponse response);

}
