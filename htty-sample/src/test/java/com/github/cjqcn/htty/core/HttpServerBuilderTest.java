package com.github.cjqcn.htty.core;

public class HttpServerBuilderTest {

    public static void main(String[] args) throws Exception {
        HttyServerBuilder.builder("HttyServer").build().start();
    }
}