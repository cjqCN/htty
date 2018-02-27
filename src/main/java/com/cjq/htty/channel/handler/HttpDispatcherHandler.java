package com.cjq.htty.channel.handler;

import com.cjq.htty.core.HandlerInvokeBean;
import com.cjq.htty.core.HttpDispatcher;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpDispatcherHandler extends ChannelHandlerAdapter implements HttpDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(HttpDispatcherHandler.class);
    private static final HttpDispatcher DEFAULT_DISPATCHER;
    private final HttpDispatcher httpDispatcher;

    public HttpDispatcherHandler() {
        this(DEFAULT_DISPATCHER);
    }

    public HttpDispatcherHandler(final HttpDispatcher httpDispatcher) {
        this.httpDispatcher = httpDispatcher;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOG.debug("--> HttpDispatcherHandler");
        if (msg instanceof HandlerInvokeBean) {
            HandlerInvokeBean invokeBean = (HandlerInvokeBean) msg;
            if (invokeBean == null) throw new IllegalStateException("Not found invokeBean");
            // dispatch to invoke
            dispatch(invokeBean);
            LOG.debug("finshed");
        }
    }

    @Override
    public void dispatch(HandlerInvokeBean handlerInvokeBean) throws Exception {
        httpDispatcher.dispatch(handlerInvokeBean);
    }


    static {
        DEFAULT_DISPATCHER = ((invokeBean) -> {
            invokeBean.handle();
        });
    }
}
