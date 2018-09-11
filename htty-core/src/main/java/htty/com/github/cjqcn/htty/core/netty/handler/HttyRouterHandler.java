package htty.com.github.cjqcn.htty.core.netty.handler;

import htty.com.github.cjqcn.htty.core.worker.HttyWorker;
import htty.com.github.cjqcn.htty.core.common.HttyContext;
import htty.com.github.cjqcn.htty.core.router.HttyRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttyRouterHandler extends SimpleChannelInboundHandler<HttyContext> implements HttyRouter {

    private static final Logger LOG = LoggerFactory.getLogger(HttyRouterHandler.class);

    private HttyRouter delegate;

    public HttyRouterHandler(HttyRouter delegate) {
        this.delegate = delegate;
    }


    /**
     * Is called for each message of type {@link HttyContext}.
     *
     * @param ctx         the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                    belongs to
     * @param httyContext the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttyContext httyContext) throws Exception {
        LOG.debug("--> HttyRouterHandler");
        HttyWorker worker = route(httyContext);
        ctx.fireChannelRead(worker);
    }

    @Override
    public HttyWorker route(HttyContext httyContext) throws Exception {
        return delegate.route(httyContext);
    }


}