package com.cjq.htty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestRouter extends ChannelHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(BasicHttpServer.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            LOG.info(msg.toString());
        }
        ctx.fireChannelRead(msg);
    }


}
