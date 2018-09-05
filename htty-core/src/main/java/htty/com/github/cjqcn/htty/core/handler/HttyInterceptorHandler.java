package htty.com.github.cjqcn.htty.core.handler;

import htty.com.github.cjqcn.htty.core.abs.HttyContext;
import htty.com.github.cjqcn.htty.core.abs.HttyInterceptor;
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
	public boolean preHandle(HttyContext httyContext, Object handler) throws Exception {
		if (delegateIsNull()) {
			return true;
		}
		return delegate.preHandle(httyContext, handler);
	}

	@Override
	public void postHandle(HttyContext httyContext, Object handler, Object msg) throws Exception {
		if (!delegateIsNull()) {
			delegate.postHandle(httyContext, handler, msg);
		}
	}

	@Override
	public void afterCompletion(HttyContext httyContext, Object handler, Exception ex) throws Exception {
		if (!delegateIsNull()) {
			delegate.afterCompletion(httyContext, handler, ex);
		}
	}


	private boolean delegateIsNull() {
		return delegate == null;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttyContext httyContext) throws Exception {

	}
}
