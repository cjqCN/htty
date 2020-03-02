package com.github.cjqcn.htty.core.http;


/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 16:42
 **/
public enum HttyMethod {

	GET, HEAD, POST, PUT, DELETE, OPTIONS;

	private static final HttyMethod[] ALL_HTTP_METHOD = new HttyMethod[]{GET, POST, HEAD, OPTIONS, DELETE, PUT};

	public static HttyMethod[] getAllHttpMethod() {
		return ALL_HTTP_METHOD.clone();
	}
}

