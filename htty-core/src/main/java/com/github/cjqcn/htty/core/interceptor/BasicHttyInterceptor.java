package com.github.cjqcn.htty.core.interceptor;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-11 21:34
 **/
public class BasicHttyInterceptor implements HttyInterceptor {


	private static final Logger LOG = LoggerFactory.getLogger(BasicHttyInterceptor.class);

	private Iterable<? extends HttyInterceptor> httyInterceptors;

	public BasicHttyInterceptor(final Iterable<? extends HttyInterceptor> httyInterceptors) {
		LOG.info("Init BasicHttyInterceptor");
		this.httyInterceptors = httyInterceptors;
	}

	@Override
	public boolean preHandle(HttyRequest request, HttyResponse response) {
		if (!hasHttpInterceptors()) {
			return true;
		}
		for (HttyInterceptor httyInterceptor : httyInterceptors) {
			if (!httyInterceptor.preHandle(request, response)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttyRequest request, HttyResponse response) {
		if (!hasHttpInterceptors()) {
			return;
		}
		httyInterceptors.forEach(x -> x.postHandle(request, response));
	}

	private boolean hasHttpInterceptors() {
		if (httyInterceptors != null) {
			return true;
		} else {
			return false;
		}
	}
}
