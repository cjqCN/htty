package com.cjq.htty.abs;

public interface HttpRequestRouter {

    HandlerInvokeBean route(HttpRequester httpRequester, HttpResponder httpResponder) throws Exception;
}
