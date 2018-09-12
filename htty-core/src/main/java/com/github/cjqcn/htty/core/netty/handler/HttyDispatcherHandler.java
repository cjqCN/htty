package com.github.cjqcn.htty.core.netty.handler;

import com.github.cjqcn.htty.core.dispatcher.BasicHttyDispatcher;
import com.github.cjqcn.htty.core.dispatcher.HttyDispatcher;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import com.github.cjqcn.htty.core.worker.HttyWorkerWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttyDispatcherHandler extends SimpleChannelInboundHandler<HttyWorkerWrapper> implements HttyDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(HttyDispatcherHandler.class);
    private static final HttyDispatcher DEFAULT_DISPATCHER = new BasicHttyDispatcher();
    private final HttyDispatcher delegate;

    public HttyDispatcherHandler() {
        this(DEFAULT_DISPATCHER);
    }

    /**
     * Is called for each message of type {@link HttyWorker}.
     *
     * @param channelHandlerContext the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                              belongs to
     * @param httyWorkerWrapper     the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttyWorkerWrapper httyWorkerWrapper) throws Exception {
        if (httyWorkerWrapper == null) {
            throw new IllegalStateException("Not found invokeBean");
        }
        // dispatch to invoke
        dispatch(httyWorkerWrapper);
    }

    public HttyDispatcherHandler(final HttyDispatcher httyDispatcher) {
        this.delegate = httyDispatcher;
        if (delegateIsNull()) {
            throw new IllegalStateException("Unset httyDispatcher!");
        }
    }


    @Override
    public void dispatch(HttyWorkerWrapper httyWorkerWrapper) throws Exception {
        delegate.dispatch(httyWorkerWrapper);
    }


    private boolean delegateIsNull() {
        return delegate == null;
    }


}
