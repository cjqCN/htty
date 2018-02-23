package com.cjq.htty;


import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class SSLHandlerFactory {

    private final SslContext sslContext;
    private boolean needClientAuth;

    //TODO
    public SSLHandlerFactory(SSLConfig sslConfig) {
        sslContext = null;
    }

    public SSLHandlerFactory(SslContext sslContext) {
        this.sslContext = sslContext;
    }


    /**
     * @return instance of {@code SslHandler}
     */
    public SslHandler create(ByteBufAllocator bufferAllocator) {
        SSLEngine engine = sslContext.newEngine(bufferAllocator);
        engine.setNeedClientAuth(needClientAuth);
        engine.setUseClientMode(false);
        return new SslHandler(engine);
    }
}
