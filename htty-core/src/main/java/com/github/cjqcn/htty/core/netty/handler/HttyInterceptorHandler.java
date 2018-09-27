package com.github.cjqcn.htty.core.netty.handler;

import com.github.cjqcn.htty.core.common.ExceptionHandler;
import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jqChan
 * @date 2018/7/10
 */
@ChannelHandler.Sharable
public class HttyInterceptorHandler extends SimpleChannelInboundHandler<HttyContext> implements HttyInterceptor,
		ExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(HttyRouterHandler.class);

	private HttyInterceptor delegate;

	private ExceptionHandler exceptionHandler;

	public HttyInterceptorHandler(HttyInterceptor delegate, ExceptionHandler exceptionHandler) {
		LOG.info("init HttyInterceptorHandler");
		this.delegate = delegate;
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public boolean preHandle(HttyContext httyContext) {
		if (delegateIsNull()) {
			return true;
		}
		return delegate.preHandle(httyContext);
	}

	@Override
	public void postHandle(HttyContext httyContext) {
		if (!delegateIsNull()) {
			delegate.postHandle(httyContext);
		}
	}


	private boolean delegateIsNull() {
		return delegate == null;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttyContext httyContext) {
		try {
			LOG.trace("--> HttyInterceptorHandler");
			if (preHandle(httyContext)) {
				ctx.fireChannelRead(httyContext);
				postHandle(httyContext);
			} else {
				LOG.trace("postHandle returns false, connection is closed");
				ctx.close();
			}
		} catch (Exception ex) {
			handle(ex, httyContext);
		}
	}


	@Override
	public void handle(Exception ex, HttyContext httyContext) {
		LOG.error("Interceptor 异常", ex);
		exceptionHandler.handle(ex, httyContext);
	}
}
