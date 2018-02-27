package com.cjq.htty.core;

public interface HttpRequestRouter {

    HandlerInvokeBean route(HttpRequester httpRequester, HttpResponder httpResponder) throws Exception;
}
