package com.github.cjqcn.htty.core.interceptor;

public interface HttyInterceptorWrapper extends HttyInterceptor {
    HttyInterceptor get();
}
