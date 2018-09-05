package htty.com.github.cjqcn.htty.core;


import htty.com.github.cjqcn.htty.core.abs.HttpContext;
import htty.com.github.cjqcn.htty.core.abs.HttpRequester;
import htty.com.github.cjqcn.htty.core.abs.HttpResponder;

public class BasicHttpContext implements HttpContext {

	private final HttpRequester httpRequester;
	private final HttpResponder httpResponder;

	public BasicHttpContext(final HttpRequester httpRequester,
							final HttpResponder httpResponder) {
		this.httpRequester = httpRequester;
		this.httpResponder = httpResponder;
	}

	@Override
	public HttpRequester httpRequester() {
		return httpRequester;
	}

	@Override
	public HttpResponder httpResponder() {
		return httpResponder;
	}
}
