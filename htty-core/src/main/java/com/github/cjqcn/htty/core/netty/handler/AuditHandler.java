package com.github.cjqcn.htty.core.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditHandler extends ChannelInboundHandlerAdapter {


	private static final Logger LOG = LoggerFactory.getLogger(AuditHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOG.trace("--> AuditHandler");
		ByteBuf buf = (ByteBuf) msg;
		LOG.debug("\n{}", buf.toString(CharsetUtil.UTF_8));
		ctx.fireChannelRead(msg);
	}

}
