package com.github.cjqcn.htty.core.http;


public interface HttyRequest {

	HttyMethod method();

	String uri();

	String header(String name);

	Cookie[] cookies();

	String param(String name);

	String context();
}
