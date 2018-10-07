package com.github.cjqcn.htty.core.common;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-26 11:38
 **/
public class BasicExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(Exception ex, HttyRequest request, HttyResponse response) {
        response.sendString(HttpResponseStatus.INTERNAL_SERVER_ERROR, ex.toString());
        ex.printStackTrace();
    }
}
