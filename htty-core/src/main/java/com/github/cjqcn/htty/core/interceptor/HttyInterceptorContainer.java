package com.github.cjqcn.htty.core.interceptor;

import java.util.Collection;

public interface HttyInterceptorContainer extends HttyInterceptor {
	void addInterceptor(Collection<HttyInterceptor> httyInterceptors);
}
