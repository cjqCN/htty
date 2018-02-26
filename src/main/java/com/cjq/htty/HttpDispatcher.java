package com.cjq.htty;

import com.cjq.htty.abs.HandlerInvokeBean;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpDispatcher extends ChannelHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpDispatcher.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HandlerInvokeBean) {
            HandlerInvokeBean invokeBean = (HandlerInvokeBean) msg;
            // HttpInterceptors' pre-handle
            invokeBean.preHandle();
            HttpResponse httpResponse = (HttpResponse) invokeBean.invoke();
            // HttpInterceptors' post-handle
            invokeBean.postHandle();
            // Write and flush the response
            ctx.writeAndFlush(httpResponse);
            // HttpInterceptors' afterCompletion
            invokeBean.afterCompletion();
        }

    }

}
