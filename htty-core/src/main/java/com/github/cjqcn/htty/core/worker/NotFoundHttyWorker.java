package com.github.cjqcn.htty.core.worker;

import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

public class NotFoundHttyWorker implements HttyWorker {
	@Override
	public void handle(HttyRequest httyRequest, HttyResponse httyResponse) throws Exception {
		httyResponse.sendStatus(HttpResponseStatus.NOT_FOUND);
	}

	@Override
	public HttyMethod[] HttpMethod() {
		throw new IllegalStateException("Not supported to call");
	}

	@Override
	public String path() {
		throw new IllegalStateException("Not supported to call");
	}
}
