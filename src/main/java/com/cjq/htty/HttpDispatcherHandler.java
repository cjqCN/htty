package com.cjq.htty;

import com.cjq.htty.abs.HandlerInvokeBean;
import com.cjq.htty.abs.HttpDispatcher;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpDispatcherHandler extends ChannelHandlerAdapter implements HttpDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(HttpDispatcherHandler.class);
    private static final HttpDispatcher DEFAULT_DISPATCHER;
    private final HttpDispatcher httpDispatcher;

    public HttpDispatcherHandler() {
        this(DEFAULT_DISPATCHER);
    }

    public HttpDispatcherHandler(final HttpDispatcher httpDispatcher) {
        this.httpDispatcher = httpDispatcher;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HandlerInvokeBean) {
            HandlerInvokeBean invokeBean = (HandlerInvokeBean) msg;
            // dispatch to invoke
            dispatch(invokeBean);
        }
    }

    @Override
    public void dispatch(HandlerInvokeBean handlerInvokeBean) throws Exception {
        httpDispatcher.dispatch(handlerInvokeBean);
    }


    static {
        /**
         * 1.Execute the pre-handle method
         * 2.Execute the main method
         * 3.Execute the post-handle method
         * 4.Execute the after-completion method
         */
        DEFAULT_DISPATCHER = ((invokeBean) -> {
            // HttpInterceptors' pre-handle
            invokeBean.preHandle();
            HttpResponse httpResponse = (HttpResponse) invokeBean.invoke();
            // HttpInterceptors' post-handle
            invokeBean.postHandle();
            // HttpInterceptors' afterCompletion
            invokeBean.afterCompletion();
        });
    }
}
