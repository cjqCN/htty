package htty.com.github.cjqcn.htty.core.interceptor;


import htty.com.github.cjqcn.htty.core.common.HttyContext;

public interface HttyInterceptor {

    boolean preHandle(HttyContext httyContext);

    void postHandle(HttyContext httyContext);

}
