package com.cjq.htty.core;

public interface URLRewriter {
	/**
	 *
	 * @param httpContext
	 * @return
	 * @throws Exception
	 */
	boolean rewrite(HttpContext httpContext) throws Exception;
}
