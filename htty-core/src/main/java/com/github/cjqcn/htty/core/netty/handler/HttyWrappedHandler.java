package com.github.cjqcn.htty.core.netty.handler;


import com.github.cjqcn.htty.core.common.BasicHttyContext;
import com.github.cjqcn.htty.core.http.BasicHttyRequest;
import com.github.cjqcn.htty.core.http.BasicHttyResponse;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttyWrappedHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static final Logger LOG = LoggerFactory.getLogger(HttyWrappedHandler.class);


	/**
	 * Is called for each message of type {@link HttpRequest}.
	 *
	 * @param channelHandlerContext the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
	 *                              belongs to
	 * @param fullHttpRequest           the httpRequest to handle
	 * @throws Exception is thrown if an error occurred
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
		LOG.trace("--> HttyWrappedHandler");
		HttyRequest requester = new BasicHttyRequest(fullHttpRequest);
		HttyResponse responder = new BasicHttyResponse(channelHandlerContext.channel(), false);
		channelHandlerContext.fireChannelRead(new BasicHttyContext(requester, responder));
	}
}
