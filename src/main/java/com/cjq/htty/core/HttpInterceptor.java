package com.cjq.htty.core;


public interface HttpInterceptor {

    boolean preHandle(HttpContext httpContext, Object handler)
            throws Exception;

    void postHandle(HttpContext httpContext, Object handler, Object msg)
            throws Exception;

    void afterCompletion(HttpContext httpContext, Object handler, Exception ex)
            throws Exception;

}
