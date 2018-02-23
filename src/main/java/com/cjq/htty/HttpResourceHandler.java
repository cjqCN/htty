package com.cjq.htty;

import com.cjq.htty.abs.*;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HttpResourceHandler implements HttpHandler {

    private final Iterable<? extends HttpHandler> httpHandlers;
    private final Iterable<? extends HandlerHook> handlerHooks;
    private final URLRewriter urlRewriter;
    private final ExceptionHandler exceptionHandler;

    public HttpResourceHandler(final Iterable<? extends HttpHandler> httpHandlers,
                               final Iterable<? extends HandlerHook> handlerHooks,
                               final URLRewriter urlRewriter,
                               final ExceptionHandler exceptionHandler) {
        this.httpHandlers = httpHandlers;
        this.handlerHooks = handlerHooks;
        this.urlRewriter = urlRewriter;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void init(HandlerContext context) {

    }

    @Override
    public void destroy(HandlerContext context) {

    }

    public void handle(HttpRequest request, HttpResponder responder) {

    }
}
