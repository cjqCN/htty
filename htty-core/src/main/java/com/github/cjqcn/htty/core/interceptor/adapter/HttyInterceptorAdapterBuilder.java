package com.github.cjqcn.htty.core.interceptor.adapter;

import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-09 11:53
 **/
public final class HttyInterceptorAdapterBuilder {

	public static HttyInterceptorAdapter create(HttyInterceptor httyInterceptor) {
		return new ProxyHttyInterceptorAdapter(httyInterceptor);
	}
}
