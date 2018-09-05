package htty.com.github.cjqcn.htty.core;

import htty.com.github.cjqcn.htty.core.starter.HttpServerBuilder;

public class HttpServerBuilderTest {

    public static void main(String[] args) throws Exception {
        HttpServerBuilder.builder("HttyServer").build().start();
    }
}