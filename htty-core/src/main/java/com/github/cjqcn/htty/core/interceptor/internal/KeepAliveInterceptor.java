package com.github.cjqcn.htty.core.interceptor.internal;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;

public class KeepAliveInterceptor implements InternalInterceptor {

    @Override
    public boolean preHandle(HttyRequest request, HttyResponse response) {
        response.getHeaders().add("Connection", "Keep-Alive");
        return true;
    }


}
