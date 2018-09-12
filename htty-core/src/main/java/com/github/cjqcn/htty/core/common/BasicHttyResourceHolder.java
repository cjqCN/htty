package com.github.cjqcn.htty.core.common;


import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import com.github.cjqcn.htty.core.http.HttyHandler;

public class BasicHttyResourceHolder implements HttyResourceHolder {

    private Iterable<? extends HttyHandler> httpHandlers;
    private Iterable<? extends HttyInterceptor> httpInterceptors;

    public BasicHttyResourceHolder(final Iterable<? extends HttyHandler> httpHandlers,
                                   final Iterable<? extends HttyInterceptor> httpInterceptors) {
        this.httpHandlers = httpHandlers;
        this.httpInterceptors = httpInterceptors;
    }

    @Override
    public void setHttpHandler(Iterable<? extends HttyHandler> httpHandlers) {
        this.httpHandlers = httpHandlers;
    }

    @Override
    public void setHttpInterceptor(Iterable<? extends HttyInterceptor> httpInterceptors) {
        this.httpInterceptors = httpInterceptors;
    }

    @Override
    public Iterable<? extends HttyHandler> getHttyHandlers() {
        return httpHandlers;
    }

    @Override
    public Iterable<? extends HttyInterceptor> getHttyInterceptors() {
        return httpInterceptors;
    }

    @Override
    public void init(final HandlerContext context) throws Exception {
        for (HttyHandler httyHandler : httpHandlers) {
            httyHandler.init(context);
        }
    }

    @Override
    public void destroy(final HandlerContext context) throws Exception {
        for (HttyHandler httyHandler : httpHandlers) {
            httyHandler.destroy(context);
        }
    }
}
