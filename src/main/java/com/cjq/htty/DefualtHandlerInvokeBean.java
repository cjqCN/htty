package com.cjq.htty;

import com.cjq.htty.core.*;

import java.lang.reflect.Method;

public class DefualtHandlerInvokeBean implements HandlerInvokeBean {

	private final HttpContext httpContext;
	private final Method mainMethod;
	private final Iterable<? extends HttpInterceptor> httpInterceptors;
	private final ExceptionHandler exceptionHandler;

	/**
	 * @param httpContext
	 * @param mainMethod
	 * @param httpInterceptors
	 * @param exceptionHandler
	 */
	public DefualtHandlerInvokeBean(final HttpContext httpContext,
									final Method mainMethod,
									final Iterable<? extends HttpInterceptor> httpInterceptors,
									final ExceptionHandler exceptionHandler) {
		this.httpContext = httpContext;
		this.mainMethod = mainMethod;
		this.httpInterceptors = httpInterceptors;
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * 1.Execute the pre-handle method
	 * 2.Execute the main method
	 * 3.Execute the post-handle method
	 * 4.Execute the after-completion method
	 */
	@Override
	public void handle() throws Exception {
		try {
			//Stop running main method if returns fasle
			if (!preHandle())
				return;
			invoke();
			postHandle();
		} catch (Exception ex) {
			exceptionHandle(ex);
		} finally {
			afterCompletion();
		}
	}

	boolean preHandle() throws Exception {
		if (httpInterceptors == null)
			return true;
		// HttpInterceptors'chain
		for (HttpInterceptor httpInterceptor : httpInterceptors) {
			// Use reflect to get channel
			Object handler = httpInterceptor.getClass()
					.getMethod("preHandle").getParameterTypes()[2];
			if (!httpInterceptor.preHandle(httpContext, handler)) {
				return false;
			}
		}
		return true;
	}

	void invoke() throws Exception {
		mainMethod.invoke(httpContext);
	}

	void postHandle() throws Exception {
	}

	void afterCompletion() throws Exception {
	}

	void exceptionHandle(Exception ex) throws Exception {
		exceptionHandler.handle(ex, httpContext);
	}

}
