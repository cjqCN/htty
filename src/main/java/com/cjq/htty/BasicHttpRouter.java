package com.cjq.htty;

import com.cjq.htty.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicHttpRouter implements HttpRouter {

    private static final Logger LOG = LoggerFactory.getLogger(BasicHttpRouter.class);

    private final HttpResourceHandler httpResourceHandler;
    private final Iterable<? extends HttpHandler> httpHandlers;
    private final Iterable<? extends HttpInterceptor> httpInterceptors;

    BasicHttpRouter(final HttpResourceHandler httpResourceHandler) throws Exception {
        this.httpResourceHandler = httpResourceHandler;
        this.httpHandlers = httpResourceHandler.getHttpHandlers();
        this.httpInterceptors = httpResourceHandler.getHttpInterceptors();
    }

    @Override
    public HandlerInvokeBean route(HttpContext httpContext) throws Exception {
        if (!checkHasHttpHandlers()) {
            return new NotFoundHandlerInvokeBean(httpContext);
        }
        // TODO
        return new NotFoundHandlerInvokeBean(httpContext);
    }

    private boolean checkHasHttpHandlers() {
        return httpHandlers != null;
    }

}
