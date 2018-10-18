package com.github.cjqcn.htty.sample.interceptor;

import com.github.cjqcn.htty.core.HttyServerBuilder;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import com.github.cjqcn.htty.core.interceptor.adapter.AbstractHttyInterceptorAdapter;

import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:20
 **/
public class InterceptorServer {

	public static void main(String[] args) throws Exception {
		HttyServerBuilder.builder("InterceptorServer")
				.setPort(8081)
				.addHttyInterceptor(new PermissionInterceptor()) // 默认优先级是 1024
				.addHttyInterceptor(new HelloInterceptor().setPriority(200).includeUrlPatterns("/hello/**"))
				.build().start();
	}


	static class PermissionInterceptor implements HttyInterceptor {

		@Override
		public boolean preHandle(HttyRequest request, HttyResponse response) {
			response.sendString(FORBIDDEN, "无权限");
			return false;
		}

		@Override
		public void postHandle(HttyRequest request, HttyResponse response) {

		}
	}

	static class HelloInterceptor extends AbstractHttyInterceptorAdapter {

		@Override
		public boolean preHandle(HttyRequest request, HttyResponse response) {
			response.sendString(OK, "hellworld");
			return false;
		}

		@Override
		public void postHandle(HttyRequest request, HttyResponse response) {

		}
	}

}
