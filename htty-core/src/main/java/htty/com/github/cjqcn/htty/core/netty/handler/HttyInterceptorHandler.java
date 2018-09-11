package htty.com.github.cjqcn.htty.core.netty.handler;

import htty.com.github.cjqcn.htty.core.common.HttyContext;
import htty.com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jqChan
 * @date 2018/7/10
 */
public class HttyInterceptorHandler extends SimpleChannelInboundHandler<HttyContext> implements HttyInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(HttyRouterHandler.class);

    private HttyInterceptor delegate;

    public HttyInterceptorHandler(HttyInterceptor delegate) {
        this.delegate = delegate;
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
        if (!delegateIsNull())
            delegate.postHandle(httyContext);
    }


    private boolean delegateIsNull() {
        return delegate == null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttyContext httyContext) {
        if (preHandle(httyContext)) {
            ctx.fireChannelRead(httyContext);
            postHandle(httyContext);
        } else {
            ctx.close();
        }
    }


}
