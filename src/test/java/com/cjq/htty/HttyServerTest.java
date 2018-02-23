package com.cjq.htty;


public class HttyServerTest {

    public static void main(String[] args) throws Exception {
        HttpServer httyServer = HttpServer.builder("test").setPort(10).build();
        httyServer.start();
    }
}