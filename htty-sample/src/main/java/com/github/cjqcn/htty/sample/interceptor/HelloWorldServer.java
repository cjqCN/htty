package com.github.cjqcn.htty.sample.interceptor;

import com.github.cjqcn.htty.core.HttyServerBuilder;
import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:20
 **/
public class HelloWorldServer {

    public static void main(String[] args) throws Exception {
        HttyServerBuilder.builder("HelloWorldServer")
                .setPort(8080)
                .addHttyInterceptor(new HelloWorldInterceptor())
                .build().start();
    }


    static class HelloWorldInterceptor implements HttyInterceptor {

        @Override
        public boolean preHandle(HttyContext httyContext) {
            if (httyContext.httyRequest().uri().equals("/hello")) {
                httyContext.httyResponse().sendString(OK, "hello world");
            }
            return false;
        }

        @Override
        public void postHandle(HttyContext httyContext) {

        }
    }
}
