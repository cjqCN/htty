package com.github.cjqcn.htty.core.interceptor.adapter;

import com.github.cjqcn.htty.core.common.Priority;
import com.github.cjqcn.htty.core.common.UrlPatternMatchEnable;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;

import java.util.Collection;

public interface HttyInterceptorAdapter extends HttyInterceptor, Priority, UrlPatternMatchEnable {

    @Override
    default boolean preHandle(HttyRequest request, HttyResponse response) {
        return true;
    }

    @Override
    default void postHandle(HttyRequest request, HttyResponse response) {
    }

    @Override
    int getPriority();

    @Override
    Collection<String> getIncludeUrlPatterns();

    @Override
    Collection<String> getExcludeUrlPatterns();

}
