package com.github.cjqcn.htty.sample.helloworld;

import com.github.cjqcn.htty.core.HttyServerBuilder;
import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.worker.HttyWorker;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:20
 **/
public class HelloWorldServer {

    public static void main(String[] args) throws Exception {
        HttyServerBuilder.builder("InterceptorServer")
                .setPort(8080)
                .addHttyHandler(new HelloWorldHandler())
                .build().start();
    }


    static class HelloWorldHandler implements HttyWorker {
        @Override
        public void handle(HttyRequest httyRequest, HttyResponse httyResponse) {
            httyResponse.sendString(OK, "hello world");
        }

        @Override
        public HttyMethod[] HttpMethod() {
            return HttyMethod.ALL_HTTP_METHOD;
        }

        @Override
        public String path() {
            return "/hello";
        }
    }
}
