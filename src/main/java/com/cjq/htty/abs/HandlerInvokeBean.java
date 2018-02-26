package com.cjq.htty.abs;

public interface HandlerInvokeBean extends HttpInterceptor {

    boolean preHandle() throws Exception;

    Object invoke() throws Exception;

    void postHandle() throws Exception;

    void afterCompletion() throws Exception;
}
