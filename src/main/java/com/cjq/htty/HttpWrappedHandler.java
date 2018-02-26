package com.cjq.htty;

import com.cjq.htty.abs.HttpRequester;
import com.cjq.htty.abs.HttpResponder;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class HttpWrappedHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequester requester = new BasicHttpRequester((HttpRequest) msg);
            HttpResponder responder = new BasicHttpResponder(null);
            ctx.fireChannelRead(new BasicHttpWrapper(requester, responder));
        }
        ctx.fireChannelRead(msg);
    }

}
