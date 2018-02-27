package com.cjq.htty;

import com.cjq.htty.core.HandlerContext;
import com.cjq.htty.core.HttpHandler;
import com.cjq.htty.core.HttpInterceptor;
import com.cjq.htty.core.HttpResourceHandler;

public class BasicHttpResourceHandler implements HttpResourceHandler {

    private Iterable<? extends HttpHandler> httpHandlers;
    private Iterable<? extends HttpInterceptor> httpInterceptors;

    public BasicHttpResourceHandler(final Iterable<? extends HttpHandler> httpHandlers,
                                    final Iterable<? extends HttpInterceptor> httpInterceptors) {
        this.httpHandlers = httpHandlers;
        this.httpInterceptors = httpInterceptors;
    }

    @Override
    public void setHttpHandler(Iterable<? extends HttpHandler> httpHandlers) throws Exception {
        this.httpHandlers = httpHandlers;
    }

    @Override
    public void setHttpInterceptor(Iterable<? extends HttpInterceptor> httpInterceptors) throws Exception {
        this.httpInterceptors = httpInterceptors;
    }

    @Override
    public Iterable<? extends HttpHandler> getHttpHandlers() throws Exception {
        return httpHandlers;
    }

    @Override
    public Iterable<? extends HttpInterceptor> getHttpInterceptors() throws Exception {
        return httpInterceptors;
    }

    @Override
    public void init(final HandlerContext context) throws Exception {
        for (HttpHandler httpHandler : httpHandlers) {
            httpHandler.init(context);
        }
    }

    @Override
    public void destroy(final HandlerContext context) throws Exception {
        for (HttpHandler httpHandler : httpHandlers) {
            httpHandler.destroy(context);
        }
    }
}
