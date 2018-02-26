package com.cjq.htty;

import com.cjq.htty.abs.*;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrappedHttpRequestRouter extends ChannelHandlerAdapter implements HttpRequestRouter {

    private static final Logger LOG = LoggerFactory.getLogger(WrappedHttpRequestRouter.class);

    private HttpRequestRouter delegate;

    public WrappedHttpRequestRouter(HttpRequestRouter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpWrapper) {
            LOG.debug(((HttpWrapper) msg).httpRequester().toString());
            HttpWrapper wrapper = (HttpWrapper) msg;
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