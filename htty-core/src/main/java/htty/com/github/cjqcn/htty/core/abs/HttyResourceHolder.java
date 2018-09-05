package htty.com.github.cjqcn.htty.core.abs;

public interface HttyResourceHolder extends HttyHandler {

	void setHttpHandler(Iterable<? extends HttyHandler> httpHandlers) throws Exception;

	void setHttpInterceptor(Iterable<? extends HttyInterceptor> httpInterceptors) throws Exception;

	Iterable<? extends HttyHandler> getHttpHandlers() throws Exception;

	Iterable<? extends HttyInterceptor> getHttpInterceptors() throws Exception;

}
