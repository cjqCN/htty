package com.cjq.htty.core;


public abstract class ExceptionHandler {
	public abstract void handle(Exception ex, HttpContext httpContext) throws Exception;
}
