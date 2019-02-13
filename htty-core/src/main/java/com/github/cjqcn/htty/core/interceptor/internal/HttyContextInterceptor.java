package com.github.cjqcn.htty.core.interceptor.internal;

import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.interceptor.adapter.HttyInterceptorAdapter;
import com.github.cjqcn.htty.core.interceptor.adapter.ProxyHttyInterceptorAdapter;
import io.netty.util.concurrent.FastThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static com.github.cjqcn.htty.core.common.HttyContext.REQUEST_FIELD_NAME;
import static com.github.cjqcn.htty.core.common.HttyContext.RESPONSE_FIELD_NAME;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-09 13:58
 **/
public class HttyContextInterceptor implements InternalInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(HttyContextInterceptor.class);

	private FastThreadLocal<HttyRequest> httyRequestThreadLocal;
	private FastThreadLocal<HttyResponse> httyResponseThreadLocal;

	public HttyContextInterceptor() {
		init();
	}

	private void init() {
		LOG.info("Init HttyContextInterceptor");
		try {
			Class<HttyContext> httyContextClass = HttyContext.class;

			Field httyRequestField = httyContextClass.getDeclaredField(REQUEST_FIELD_NAME);
			httyRequestField.setAccessible(true);
			httyRequestThreadLocal= (FastThreadLocal<HttyRequest>) httyRequestField.get(httyContextClass);

			Field httyResponseField = httyContextClass.getDeclaredField(RESPONSE_FIELD_NAME);
			httyResponseField.setAccessible(true);
			httyResponseThreadLocal= (FastThreadLocal<HttyResponse>) httyResponseField.get(httyContextClass);

		} catch (Exception ex) {
			LOG.error("HttyContextInterceptor init error", ex);
		}
	}

	@Override
	public boolean preHandle(HttyRequest request, HttyResponse response) {
		try {
			httyRequestThreadLocal.set(request);
			httyResponseThreadLocal.set(response);
		} catch (NullPointerException ex) {
			LOG.error("HttyContextInterceptor preHandle error", ex);
		}
		return true;
	}

	@Override
	public void postHandle(HttyRequest request, HttyResponse response) {
		try {
			httyRequestThreadLocal.remove();
			httyResponseThreadLocal.remove();
		} catch (NullPointerException ex) {
			LOG.error("HttyContextInterceptor postHandle error", ex);
		}
	}

	public HttyInterceptorAdapter getHttyInterceptorAdapter() {
		return new ProxyHttyInterceptorAdapter(this).setPriority(-1024);
	}
}
