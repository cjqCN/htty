package htty.com.github.cjqcn.htty.core;

import static org.junit.Assert.*;

public class HttpServerBuilderTest {

    public static void main(String[] args) throws Exception {
        HttpServerBuilder.builder("HttpServer").build().start();
    }
}