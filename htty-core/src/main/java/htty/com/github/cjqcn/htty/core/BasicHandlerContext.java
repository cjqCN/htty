package htty.com.github.cjqcn.htty.core;


import htty.com.github.cjqcn.htty.core.abs.HandlerContext;
import htty.com.github.cjqcn.htty.core.abs.HttpResourceHolder;

public class BasicHandlerContext implements HandlerContext {

	private final HttpResourceHolder httpResourceHolder;

	public BasicHandlerContext(HttpResourceHolder httpResourceHolder) {
		this.httpResourceHolder = httpResourceHolder;
	}

	@Override
	public HttpResourceHolder getHttpResourceHolder() {
		return httpResourceHolder;
	}
}
