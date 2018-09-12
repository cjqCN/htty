package com.github.cjqcn.htty.core.common;


public class BasicHandlerContext implements HandlerContext {

    private final HttyResourceHolder httpResourceHolder;

    public BasicHandlerContext(HttyResourceHolder httpResourceHolder) {
        this.httpResourceHolder = httpResourceHolder;
    }

    @Override
    public HttyResourceHolder getHttyResourceHolder() {
        return httpResourceHolder;
    }
}
