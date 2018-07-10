package com.cjq.htty;

import com.cjq.htty.core.HandlerInvokeBean;
import com.cjq.htty.core.HttpContext;
import io.netty.handler.codec.http.HttpResponseStatus;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class NotFoundHandlerInvokeBean implements HandlerInvokeBean {

	final HttpContext httpContext;

	public NotFoundHandlerInvokeBean(HttpContext httpContext) {
		this.httpContext = httpContext;
	}


	@Override
	public void handle() throws Exception {
		//httpContext.httpResponder().sendStatus(HttpResponseStatus.NOT_FOUND);
		httpContext.httpResponder().sendString(OK,"OK");
	}

}
