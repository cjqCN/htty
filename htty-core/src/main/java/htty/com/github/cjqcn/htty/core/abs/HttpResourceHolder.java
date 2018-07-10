package htty.com.github.cjqcn.htty.core.abs;

public interface HttpResourceHolder extends HttpHandler {

    void setHttpHandler(Iterable<? extends HttpHandler> httpHandlers) throws Exception;

    void setHttpInterceptor(Iterable<? extends HttpInterceptor> httpInterceptors) throws Exception;

    Iterable<? extends HttpHandler> getHttpHandlers() throws Exception;

    Iterable<? extends HttpInterceptor> getHttpInterceptors() throws Exception;

}
