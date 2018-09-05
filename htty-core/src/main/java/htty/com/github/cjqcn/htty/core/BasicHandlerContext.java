package htty.com.github.cjqcn.htty.core;


import htty.com.github.cjqcn.htty.core.abs.HandlerContext;
import htty.com.github.cjqcn.htty.core.abs.HttyResourceHolder;

public class BasicHandlerContext implements HandlerContext {

	private final HttyResourceHolder httpResourceHolder;

	public BasicHandlerContext(HttyResourceHolder httpResourceHolder) {
		this.httpResourceHolder = httpResourceHolder;
	}

	@Override
	public HttyResourceHolder getHttyResourceHolder() {
		return httpResourceHolder;
	}
}
