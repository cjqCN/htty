package com.cjq.htty;

public class HttpServerBuilderTest {

	public static void main(String[] args) throws Exception {
		HttpServerBuilder.builder("HttpServer").build().start();
	}
}