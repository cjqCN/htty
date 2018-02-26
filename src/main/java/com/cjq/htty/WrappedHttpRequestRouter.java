package com.cjq.htty;

import com.cjq.htty.abs.HttpHandler;
import com.cjq.htty.abs.HttpRequestRouter;
import com.cjq.htty.abs.HttpRequester;
import com.cjq.htty.abs.HttpResponder;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
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
        if (msg instanceof HttpRequest) {
            LOG.info(msg.toString());
            HttpRequester requester = (HttpRequester) msg;

        }
        ctx.fireChannelRead(msg);
    }


    @Override
    public HttpHandler route(HttpRequester httpRequester, HttpResponder httpResponder) throws Exception {
        return delegate.route(httpRequester, httpResponder);
    }

}