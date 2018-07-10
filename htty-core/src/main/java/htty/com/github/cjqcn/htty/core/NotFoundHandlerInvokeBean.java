package htty.com.github.cjqcn.htty.core;

import htty.com.github.cjqcn.htty.core.abs.HandlerInvokeBean;
import htty.com.github.cjqcn.htty.core.abs.HttpContext;

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
