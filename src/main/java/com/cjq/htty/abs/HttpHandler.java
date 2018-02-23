package com.cjq.htty.abs;

public interface HttpHandler {

    void init(HandlerContext context);

    void destroy(HandlerContext context);

}
