package com.github.cjqcn.htty.core.common;

import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import com.github.cjqcn.htty.core.http.HttyHandler;

public interface HttyResourceHolder extends HttyHandler {

    void setHttpHandler(Iterable<? extends HttyHandler> httyHandlers);

    void setHttpInterceptor(Iterable<? extends HttyInterceptor> httyInterceptors);

    Iterable<? extends HttyHandler> getHttyHandlers();

    Iterable<? extends HttyInterceptor> getHttyInterceptors();

}
