package com.cjq.htty.abs;

public interface HttpRequestRouter {

    HttpHandler route(HttpRequester httpRequester, HttpResponder httpResponder) throws Exception;
}
