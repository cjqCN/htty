package com.cjq.htty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class RequestRouter extends ChannelHandlerAdapter {

    private final int chunkMemoryLimit;
    private final HttpResourceHandler httpMethodHandler;
    private final AtomicBoolean exceptionRaised;
    private final boolean sslEnabled;

    public RequestRouter(HttpResourceHandler methodHandler, int chunkMemoryLimit, boolean sslEnabled) {
        this.httpMethodHandler = methodHandler;
        this.chunkMemoryLimit = chunkMemoryLimit;
        this.exceptionRaised = new AtomicBoolean(false);
        this.sslEnabled = sslEnabled;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (exceptionRaised.get()) {
            return;
        }
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            log.info(request.toString());
            log.info(Thread.currentThread().getName());
        }
    }


}
