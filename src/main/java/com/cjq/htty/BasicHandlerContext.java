package com.cjq.htty;

import com.cjq.htty.abs.HandlerContext;
import com.cjq.htty.abs.HttpResourceHandler;

public class BasicHandlerContext implements HandlerContext {

    private final HttpResourceHandler httpResourceHandler;

    public BasicHandlerContext(HttpResourceHandler httpResourceHandler) {
        this.httpResourceHandler = httpResourceHandler;
    }

    @Override
    public HttpResourceHandler getHttpResourceHandler() {
        return httpResourceHandler;
    }
}
