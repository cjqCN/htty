package htty.com.github.cjqcn.htty.core;

import htty.com.github.cjqcn.htty.core.abs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicHttpRouter implements HttpRouter {

    private static final Logger LOG = LoggerFactory.getLogger(BasicHttpRouter.class);

    private final HttpResourceHolder httpResourceHolder;
    private final Iterable<? extends HttpHandler> httpHandlers;
    private final Iterable<? extends HttpInterceptor> httpInterceptors;

    BasicHttpRouter(final HttpResourceHolder httpResourceHolder) throws Exception {
        this.httpResourceHolder = httpResourceHolder;
        this.httpHandlers = httpResourceHolder.getHttpHandlers();
        this.httpInterceptors = httpResourceHolder.getHttpInterceptors();
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
