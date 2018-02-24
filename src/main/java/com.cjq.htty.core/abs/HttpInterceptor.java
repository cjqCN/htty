package com.cjq.htty.core.abs;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public interface HttpInterceptor {

    boolean preHandle(HttpRequest request, HttpResponse response, Object handler)
            throws Exception;


    void postHandle(HttpRequest request, HttpResponse response, Object handler, Object msg)
            throws Exception;


    void afterCompletion(HttpRequest request, HttpResponse response, Object handler, Exception ex)
            throws Exception;

}
