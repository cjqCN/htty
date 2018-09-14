package com.github.cjqcn.htty.core.common;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class SSLHandlerFactory {

	private final SslContext sslContext;
	private boolean needClientAuth;

	SSLHandlerFactory(final SslContext sslContext,
					  final boolean needClientAuth) {
		this.sslContext = sslContext;
		this.needClientAuth = needClientAuth;
	}


	public SslHandler create(ByteBufAllocator bufferAllocator) {
		SSLEngine engine = sslContext.newEngine(bufferAllocator);
		engine.setNeedClientAuth(needClientAuth);
		engine.setUseClientMode(false);
		return new SslHandler(engine);
	}
}
