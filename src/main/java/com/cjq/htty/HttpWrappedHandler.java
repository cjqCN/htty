package com.cjq.htty;

import com.cjq.htty.abs.HttpRequester;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class HttpWrappedHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequester requester = (HttpRequester) msg;


        }
        ctx.fireChannelRead(msg);
    }

}
