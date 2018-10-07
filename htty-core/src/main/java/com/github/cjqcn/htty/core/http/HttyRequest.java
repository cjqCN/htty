package com.github.cjqcn.htty.core.http;


import com.github.cjqcn.htty.core.http.cookie.HttyCookie;

public interface HttyRequest {

	HttyMethod method();

	String uri();

	String header(String name);

	HttyCookie[] cookies();

	String param(String name);

	String context();
}
