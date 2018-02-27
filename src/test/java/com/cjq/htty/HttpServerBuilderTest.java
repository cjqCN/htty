package com.cjq.htty;

import com.cjq.htty.abs.HttpServer;
import org.junit.Test;


public class HttpServerBuilderTest {

    @Test
    public void test() throws Exception {
        HttpServer httpServer = HttpServerBuilder.builder("test").build();
        httpServer.start();
    }


}