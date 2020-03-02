package com.github.cjqcn.htty.sample.helloworld;

import com.github.cjqcn.htty.core.HttyServerBuilder;
import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.worker.HttyWorker;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @author: chenjinquan
 * @create: 2018-09-14 17:20
 **/
public class HelloWorldServer {

	public static void main(String[] args) throws Exception {
		HttyServerBuilder.builder("HelloWorldServer")
				.setPort(8080)
				.addHandler(new HelloWorldHandler())
				.build().start();
	}


	static class HelloWorldHandler implements HttyWorker {
		@Override
		public void handle(HttyRequest httyRequest, HttyResponse httyResponse) {
			httyResponse.sendString(OK, "hello world");
		}

		@Override
		public HttyMethod[] httpMethod() {
			return HttyMethod.getAllHttpMethod();
		}

		@Override
		public String path() {
			return "/hello";
		}
	}
}
