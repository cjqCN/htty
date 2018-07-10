package com.cjq.htty.channel.handler;

import com.cjq.htty.BasicHttpRequester;
import com.cjq.htty.BasicHttpResponder;
import com.cjq.htty.BasicHttpContext;
import com.cjq.htty.core.HttpRequester;
import com.cjq.htty.core.HttpResponder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWrappedHandler extends SimpleChannelInboundHandler<HttpRequest> {

	private static final Logger LOG = LoggerFactory.getLogger(HttpWrappedHandler.class);


	/**
	 * Is called for each message of type {@link HttpRequest}.
	 *
	 * @param ctx         the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
	 *                    belongs to
	 * @param httpRequest the httpRequest to handle
	 * @throws Exception is thrown if an error occurred
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, HttpRequest httpRequest) throws Exception {
		LOG.debug("init BasicHttpContext");
		HttpRequester requester = new BasicHttpRequester(httpRequest);
		HttpResponder responder = new BasicHttpResponder(ctx.channel(), false);
		ctx.fireChannelRead(new BasicHttpContext(requester, responder));
	}

}
