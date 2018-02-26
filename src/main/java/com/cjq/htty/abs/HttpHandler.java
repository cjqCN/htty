package com.cjq.htty.abs;

public interface HttpHandler {

    void init(HandlerContext context) throws Exception;

    void destroy(HandlerContext context) throws Exception;
}
