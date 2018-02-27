package com.cjq.htty;

import com.cjq.htty.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicHttpRequestRouter implements HttpRequestRouter {

    private static final Logger LOG = LoggerFactory.getLogger(BasicHttpRequestRouter.class);

    private final HttpResourceHandler httpResourceHandler;
    private final Iterable<? extends HttpHandler> httpHandlers;
    private final Iterable<? extends HttpInterceptor> httpInterceptors;

    BasicHttpRequestRouter(final HttpResourceHandler httpResourceHandler) throws Exception {
        this.httpResourceHandler = httpResourceHandler;
        this.httpHandlers = httpResourceHandler.getHttpHandlers();
        this.httpInterceptors = httpResourceHandler.getHttpInterceptors();
    }

    @Override
    public HandlerInvokeBean route(HttpRequester httpRequester, HttpResponder httpResponder) throws Exception {
        if (!checkHasHttpHandlers()) {
            return new NotFoundHandlerInvokeBean(httpRequester, httpResponder);
        }
        // TODO
        return new NotFoundHandlerInvokeBean(httpRequester, httpResponder);
    }

    private boolean checkHasHttpHandlers() {
        return httpHandlers != null;
    }

}
