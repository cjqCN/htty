package com.cjq.htty.channel.handler;

import com.cjq.htty.BasicHttpRequester;
import com.cjq.htty.BasicHttpResponder;
import com.cjq.htty.BasicHttpWrapper;
import com.cjq.htty.core.HttpRequester;
import com.cjq.htty.core.HttpResponder;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWrappedHandler extends ChannelHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpWrappedHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOG.debug("--> HttpWrappedHandler");
        if (msg instanceof HttpRequest) {
            HttpRequester requester = new BasicHttpRequester((HttpRequest) msg);
            HttpResponder responder = new BasicHttpResponder(ctx.channel(), false);
            ctx.fireChannelRead(new BasicHttpWrapper(requester, responder));
        }
        ctx.fireChannelRead(msg);
    }

}
