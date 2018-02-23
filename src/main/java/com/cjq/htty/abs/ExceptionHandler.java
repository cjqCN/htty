package com.cjq.htty.abs;

import io.netty.handler.codec.http.HttpRequest;

public interface ExceptionHandler {
    void handle(Throwable t, HttpRequest request, HttpResponder responder);
}
