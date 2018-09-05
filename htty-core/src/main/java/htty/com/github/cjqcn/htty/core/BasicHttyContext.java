package htty.com.github.cjqcn.htty.core;


import htty.com.github.cjqcn.htty.core.abs.HttyContext;
import htty.com.github.cjqcn.htty.core.abs.HttyRequest;
import htty.com.github.cjqcn.htty.core.abs.HttyResponse;

public class BasicHttyContext implements HttyContext {

	private final HttyRequest httyRequest;
	private final HttyResponse httyResponse;

	public BasicHttyContext(final HttyRequest httyRequest,
                            final HttyResponse httyResponse) {
		this.httyRequest = httyRequest;
		this.httyResponse = httyResponse;
	}

	@Override
	public HttyRequest httyRequest() {
		return httyRequest;
	}

	@Override
	public HttyResponse httyResponse() {
		return httyResponse;
	}
}
