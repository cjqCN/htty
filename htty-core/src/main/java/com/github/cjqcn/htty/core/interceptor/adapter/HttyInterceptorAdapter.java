package com.github.cjqcn.htty.core.interceptor.adapter;

import com.github.cjqcn.htty.core.common.Priority;
import com.github.cjqcn.htty.core.common.UrlPatternMatchEnable;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptorWrapper;

public interface HttyInterceptorAdapter extends HttyInterceptorWrapper, Priority, UrlPatternMatchEnable {
}
