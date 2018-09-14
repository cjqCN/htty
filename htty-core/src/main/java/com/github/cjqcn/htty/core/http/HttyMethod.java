package com.github.cjqcn.htty.core.http;


/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 16:42
 **/
public class HttyMethod {


	public static final HttyMethod GET = new HttyMethod("GET");
	public static final HttyMethod POST = new HttyMethod("POST");
	public static final HttyMethod HEAD = new HttyMethod("HEAD");

	public static final HttyMethod OPTIONS = new HttyMethod("OPTIONS");
	public static final HttyMethod DELETE = new HttyMethod("DELETE");
	public static final HttyMethod PUT = new HttyMethod("PUT");


	private String name;

	private HttyMethod(String name) {
		this.name = name;
	}

	public static final HttyMethod[] ALL_HTTP_METHOD = new HttyMethod[]{GET, POST, HEAD, OPTIONS, DELETE, PUT};

	public static HttyMethod[] getAllHttpMethod() {
		return ALL_HTTP_METHOD;
	}

}
