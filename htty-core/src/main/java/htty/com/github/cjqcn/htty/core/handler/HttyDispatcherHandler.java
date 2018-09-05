package htty.com.github.cjqcn.htty.core.handler;

import htty.com.github.cjqcn.htty.core.abs.HttyWorker;
import htty.com.github.cjqcn.htty.core.abs.HttyDispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttyDispatcherHandler extends SimpleChannelInboundHandler<HttyWorker> implements HttyDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(HttyDispatcherHandler.class);
    private static final HttyDispatcher DEFAULT_DISPATCHER;
    private final HttyDispatcher delegate;

    public HttyDispatcherHandler() {
        this(DEFAULT_DISPATCHER);
    }

    /**
     * Is called for each message of type {@link HttyWorker}.
     *
     * @param channelHandlerContext the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                              belongs to
     * @param httyWorker     the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttyWorker httyWorker) throws Exception {
        LOG.debug("--> HttyDispatcherHandler");
        if (httyWorker == null) {
            throw new IllegalStateException("Not found invokeBean");
        }
        // dispatch to invoke
        dispatch(httyWorker);
        LOG.debug("finshed");
    }

    public HttyDispatcherHandler(final HttyDispatcher httyDispatcher) {
        this.delegate = httyDispatcher;
        if (delegateIsNull()) {
            throw new RuntimeException("Unset httyDispatcher!");
        }
    }


    @Override
    public void dispatch(HttyWorker httyWorker) throws Exception {
        delegate.dispatch(httyWorker);
    }


    private boolean delegateIsNull() {
        return delegate == null;
    }

    static {

        //TODO
        DEFAULT_DISPATCHER = ((httyWorker) -> {
//            httyWorker.handle();
        });
    }
}
