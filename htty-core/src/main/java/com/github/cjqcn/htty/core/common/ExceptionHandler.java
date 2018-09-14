package com.github.cjqcn.htty.core.common;


public abstract class ExceptionHandler {
	public abstract void handle(Exception ex, HttyContext httyContext) throws Exception;
}
