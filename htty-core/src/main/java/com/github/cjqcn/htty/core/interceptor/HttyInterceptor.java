package com.github.cjqcn.htty.core.interceptor;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;

public interface HttyInterceptor {

    default boolean preHandle(HttyRequest request, HttyResponse response) {
        return true;
    }

    default void postHandle(HttyRequest request, HttyResponse response) {
    }

}
