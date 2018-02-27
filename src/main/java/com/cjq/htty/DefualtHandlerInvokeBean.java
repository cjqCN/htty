package com.cjq.htty;

import com.cjq.htty.core.*;

import java.lang.reflect.Method;

public class DefualtHandlerInvokeBean implements HandlerInvokeBean {

    private final HttpRequester httpRequester;
    private final HttpResponder httpResponder;
    private final Method mainMethod;
    private final Iterable<? extends HttpInterceptor> httpInterceptors;
    private final ExceptionHandler exceptionHandler;

    /**
     *
     * @param httpRequester
     * @param httpResponder
     * @param mainMethod
     * @param httpInterceptors
     * @param exceptionHandler
     */
    public DefualtHandlerInvokeBean(final HttpRequester httpRequester,
                                    final HttpResponder httpResponder,
                                    final Method mainMethod,
                                    final Iterable<? extends HttpInterceptor> httpInterceptors,
                                    final ExceptionHandler exceptionHandler) {
        this.httpRequester = httpRequester;
        this.httpResponder = httpResponder;
        this.mainMethod = mainMethod;
        this.httpInterceptors = httpInterceptors;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * 1.Execute the pre-handle method
     * 2.Execute the main method
     * 3.Execute the post-handle method
     * 4.Execute the after-completion method
     */
    @Override
    public void handle() throws Exception {
        try {
            //Stop running main method if returns fasle
            if (!preHandle())
                return;
            invoke();
            postHandle();
        } catch (Exception ex) {
            exceptionHandle(ex);
        } finally {
            afterCompletion();
        }
    }

    boolean preHandle() throws Exception {
        if (httpInterceptors == null)
            return true;
        // HttpInterceptors'chain
        for (HttpInterceptor httpInterceptor : httpInterceptors) {
            // Use reflect to get channel
            Object handler = httpInterceptor.getClass()
                    .getMethod("preHandle").getParameterTypes()[2];
            if (!httpInterceptor.preHandle(httpRequester, httpResponder, handler)) {
                return false;
            }
        }
        return true;
    }

    void invoke() throws Exception {
        mainMethod.invoke(httpRequester, httpResponder);
    }

    void postHandle() throws Exception {
    }

    void afterCompletion() throws Exception {
    }

    void exceptionHandle(Exception ex) throws Exception {
        exceptionHandler.handle(ex, httpRequester, httpResponder);
    }

}
