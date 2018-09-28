package com.github.cjqcn.htty.core.interceptor;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;

public interface HttyInterceptor {

	boolean preHandle(HttyRequest request, HttyResponse response);

	void postHandle(HttyRequest request, HttyResponse response);

}
