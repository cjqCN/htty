package com.github.cjqcn.htty.core.worker;

import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-11 21:03
 **/
public class MethodNotSupportHttyWorker implements InternalWorker {

	private static final String resStrTemplate = "Request method %s not supported.";

	@Override
	public void handle(HttyRequest httyRequest, HttyResponse httyResponse) {
		httyResponse.sendString(HttpResponseStatus.METHOD_NOT_ALLOWED,
				String.format(resStrTemplate, httyRequest.method()));
	}

	@Override
	public HttyMethod[] httpMethod() {
		throw new IllegalStateException("Not supported to call");
	}

	@Override
	public String path() {
		throw new IllegalStateException("Not supported to call");
	}


}
