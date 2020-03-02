package com.github.cjqcn.htty.core.interceptor.adapter;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;

public class ProxyHttyInterceptorAdapter extends AbstractHttyInterceptorAdapter {

	private HttyInterceptor httyInterceptor;

	public ProxyHttyInterceptorAdapter(HttyInterceptor httyInterceptor) {
		this.httyInterceptor = httyInterceptor;
	}

	@Override
	public boolean preHandle(HttyRequest request, HttyResponse response) {
		return httyInterceptor.preHandle(request, response);
	}

	@Override
	public void postHandle(HttyRequest request, HttyResponse response) {
		httyInterceptor.postHandle(request, response);
	}
}
