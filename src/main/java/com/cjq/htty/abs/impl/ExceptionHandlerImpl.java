package com.cjq.htty.abs.impl;

import com.cjq.htty.abs.ExceptionHandler;
import com.cjq.htty.abs.HttpResponder;
import io.netty.handler.codec.http.HttpRequest;

public class ExceptionHandlerImpl implements ExceptionHandler {
    @Override
    public void handle(Throwable t, HttpRequest request, HttpResponder responder) {

    }
}
