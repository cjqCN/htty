package com.cjq.htty.abs;


public interface HttpInterceptor {

    boolean preHandle(HttpRequester requester, HttpResponder responder, Object handler)
            throws Exception;


    void postHandle(HttpRequester requester, HttpResponder responder, Object handler, Object msg)
            throws Exception;


    void afterCompletion(HttpRequester requester, HttpResponder responder, Object handler, Exception ex)
            throws Exception;

}
