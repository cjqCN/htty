package com.github.cjqcn.htty.core.netty.handler;

import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.router.HttyRouter;
import com.github.cjqcn.htty.core.worker.HttyWorkerWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttyRouterHandler extends SimpleChannelInboundHandler<HttyContext> implements HttyRouter {

	private static final Logger LOG = LoggerFactory.getLogger(HttyRouterHandler.class);

	private HttyRouter delegate;

	public HttyRouterHandler(HttyRouter delegate) {
		LOG.info("init HttyRouterHandler");
		this.delegate = delegate;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttyContext httyContext) throws Exception {
		LOG.trace("--> HttyRouterHandler");
		HttyWorkerWrapper httyWorkerWrapper = route(httyContext);
		LOG.trace("routed to httyWork:{}", httyWorkerWrapper.httyWorker().getClass().getName());
		ctx.fireChannelRead(httyWorkerWrapper);
	}

	@Override
	public HttyWorkerWrapper route(HttyContext httyContext) throws Exception {
		return delegate.route(httyContext);
	}


}