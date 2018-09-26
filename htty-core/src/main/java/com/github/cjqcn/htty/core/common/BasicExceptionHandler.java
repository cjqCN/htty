package com.github.cjqcn.htty.core.common;

import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-26 11:38
 **/
public class BasicExceptionHandler implements ExceptionHandler {
	@Override
	public void handle(Exception ex, HttyContext httyContext) {
		httyContext.httyResponse().sendString(HttpResponseStatus.INTERNAL_SERVER_ERROR, ex.toString());
	}
}
