package htty.com.github.cjqcn.htty.core;


import htty.com.github.cjqcn.htty.core.abs.HandlerContext;
import htty.com.github.cjqcn.htty.core.abs.HttyHandler;
import htty.com.github.cjqcn.htty.core.abs.HttyInterceptor;
import htty.com.github.cjqcn.htty.core.abs.HttyResourceHolder;

public class BasicHttyResourceHolder implements HttyResourceHolder {

	private Iterable<? extends HttyHandler> httpHandlers;
	private Iterable<? extends HttyInterceptor> httpInterceptors;

	public BasicHttyResourceHolder(final Iterable<? extends HttyHandler> httpHandlers,
								   final Iterable<? extends HttyInterceptor> httpInterceptors) {
		this.httpHandlers = httpHandlers;
		this.httpInterceptors = httpInterceptors;
	}

	@Override
	public void setHttpHandler(Iterable<? extends HttyHandler> httpHandlers) throws Exception {
		this.httpHandlers = httpHandlers;
	}

	@Override
	public void setHttpInterceptor(Iterable<? extends HttyInterceptor> httpInterceptors) throws Exception {
		this.httpInterceptors = httpInterceptors;
	}

	@Override
	public Iterable<? extends HttyHandler> getHttpHandlers() throws Exception {
		return httpHandlers;
	}

	@Override
	public Iterable<? extends HttyInterceptor> getHttpInterceptors() throws Exception {
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
