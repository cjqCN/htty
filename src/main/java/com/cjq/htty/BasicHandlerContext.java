package com.cjq.htty;

import com.cjq.htty.abs.HandlerContext;

public final class BasicHandlerContext implements HandlerContext {
    private final HttpResourceHandler httyResourceHandler;

    public BasicHandlerContext(final HttpResourceHandler httyResourceHandler) {
        this.httyResourceHandler = httyResourceHandler;
    }

    @Override
    public HttpResourceHandler getHttpResourceHandler() {
        return httyResourceHandler;
    }
}
