package com.cjq.htty;

import com.cjq.htty.abs.*;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestRouterHandler extends ChannelHandlerAdapter implements HttpRequestRouter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestRouterHandler.class);

    private HttpRequestRouter delegate;

    public HttpRequestRouterHandler(HttpRequestRouter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpWrapper) {
            HttpWrapper wrapper = (HttpWrapper) msg;
            LOG.debug(wrapper.httpRequester().toString());
            HandlerInvokeBean invokeBean = route(wrapper.httpRequester(), wrapper.httpResponder());
            ctx.fireChannelRead(invokeBean);
        }
        ctx.fireChannelRead(msg);
    }


    @Override
    public HandlerInvokeBean route(HttpRequester httpRequester, HttpResponder httpResponder) throws Exception {
        return delegate.route(httpRequester, httpResponder);
    }

}