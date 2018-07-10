package htty.com.github.cjqcn.htty.core;


import htty.com.github.cjqcn.htty.core.abs.HandlerContext;
import htty.com.github.cjqcn.htty.core.abs.HttpHandler;
import htty.com.github.cjqcn.htty.core.abs.HttpInterceptor;
import htty.com.github.cjqcn.htty.core.abs.HttpResourceHolder;

public class BasicHttpResourceHolder implements HttpResourceHolder {

    private Iterable<? extends HttpHandler> httpHandlers;
    private Iterable<? extends HttpInterceptor> httpInterceptors;

    public BasicHttpResourceHolder(final Iterable<? extends HttpHandler> httpHandlers,
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
