package com.cjq.htty.core;

public interface HttpRouter {

    HandlerInvokeBean route(HttpContext httpContext) throws Exception;
}
