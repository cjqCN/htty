package com.github.cjqcn.htty.sample.exception;

import com.github.cjqcn.htty.core.HttyServerBuilder;
import com.github.cjqcn.htty.core.common.ExceptionHandler;
import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:20
 **/
public class ExceptionServer {

	public static void main(String[] args) throws Exception {
		HttyServerBuilder.builder("ExceptionServer")
				.setPort(8082)
				.addHttyHandler(new ExceptionWorker())
				.setExceptionHandler(new MyExceptionHandler())
				.build().start();
	}

	static class ExceptionWorker implements HttyWorker {
		@Override
		public void handle(HttyRequest httyRequest, HttyResponse httyResponse) throws Exception {
			throw new Exception("Exception rasied");
		}

		@Override
		public HttyMethod[] HttpMethod() {
			return HttyMethod.ALL_HTTP_METHOD;
		}

		@Override
		public String path() {
			return "/ex";
		}
	}

	static class MyExceptionHandler implements ExceptionHandler {
		@Override
		public void handle(Exception ex, HttyContext httyContext) {
			httyContext.httyResponse().sendString(HttpResponseStatus.INTERNAL_SERVER_ERROR, "服务出错");
		}
	}
}
