package com.cjq.htty;

import com.cjq.htty.core.HandlerContext;
import com.cjq.htty.core.HttpResourceHandler;

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
