package com.cjq.htty.channel.handler;

import com.cjq.htty.core.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRouterHandler extends SimpleChannelInboundHandler<HttpContext> implements HttpRouter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpRouterHandler.class);

    private HttpRouter delegate;

    public HttpRouterHandler(HttpRouter delegate) {
        this.delegate = delegate;
    }


    /**
     * Is called for each message of type {@link HttpContext}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param httpContext the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpContext httpContext) throws Exception {
        LOG.debug("--> HttpRouterHandler");
        HttpContext wrapper = httpContext;
        HandlerInvokeBean invokeBean = route(httpContext);
        ctx.fireChannelRead(invokeBean);
    }


    @Override
    public HandlerInvokeBean route(HttpContext httpContext) throws Exception {
        return delegate.route(httpContext);
    }

}