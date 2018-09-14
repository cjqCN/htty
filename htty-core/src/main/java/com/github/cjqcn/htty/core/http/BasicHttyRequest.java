package com.github.cjqcn.htty.core.http;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;

import javax.ws.rs.NotSupportedException;

public class BasicHttyRequest implements HttyRequest {

	private final FullHttpRequest fullHttpRequest;

	public BasicHttyRequest(FullHttpRequest fullHttpRequest) {
		this.fullHttpRequest = fullHttpRequest;
	}

	@Override
	public HttyMethod method() {
		if (fullHttpRequest.method() == HttpMethod.GET) {
			return HttyMethod.GET;
		}
		if (fullHttpRequest.method() == HttpMethod.POST) {
			return HttyMethod.POST;
		}
		if (fullHttpRequest.method() == HttpMethod.HEAD) {
			return HttyMethod.HEAD;
		}
		if (fullHttpRequest.method() == HttpMethod.DELETE) {
			return HttyMethod.DELETE;
		}
		if (fullHttpRequest.method() == HttpMethod.PUT) {
			return HttyMethod.PUT;
		}
		if (fullHttpRequest.method() == HttpMethod.OPTIONS) {
			return HttyMethod.OPTIONS;
		}
		throw new NotSupportedException("不支持该方法");
	}

	@Override
	public String uri() {
		return fullHttpRequest.uri();
	}

	@Override
	public String header(String name) {
		return fullHttpRequest.headers().get(name);
	}

	@Override
	public Cookie[] cookies() {
		return new Cookie[0];
	}

	@Override
	public String param(String name) {
		return null;
	}

	@Override
	public String context() {
		return fullHttpRequest.content().toString(CharsetUtil.UTF_8);
	}
}
