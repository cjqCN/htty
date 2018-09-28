package com.github.cjqcn.htty.sample.interceptor;

import com.github.cjqcn.htty.core.HttyServerBuilder;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;

import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:20
 **/
public class InterceptorServer {

	public static void main(String[] args) throws Exception {
		HttyServerBuilder.builder("InterceptorServer")
				.setPort(8081)
				.addHttyInterceptor(new PermissionInterceptor())
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
}
