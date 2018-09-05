package htty.com.github.cjqcn.htty.core.channel.handler;

import htty.com.github.cjqcn.htty.core.abs.HandlerInvokeBean;
import htty.com.github.cjqcn.htty.core.abs.HttpDispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpDispatcherHandler extends SimpleChannelInboundHandler<HandlerInvokeBean> implements HttpDispatcher {

	private static final Logger LOG = LoggerFactory.getLogger(HttpDispatcherHandler.class);
	private static final HttpDispatcher DEFAULT_DISPATCHER;
	private final HttpDispatcher delegate;

	public HttpDispatcherHandler() {
		this(DEFAULT_DISPATCHER);
	}

	public HttpDispatcherHandler(final HttpDispatcher httpDispatcher) {
		this.delegate = httpDispatcher;
		if (delegateIsNull()) {
			throw new RuntimeException("Unset httpDispatcher!");
		}
	}


	/**
	 * Is called for each message of type {@link HandlerInvokeBean}.
	 *
	 * @param ctx               the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
	 *                          belongs to
	 * @param handlerInvokeBean the message to handle
	 * @throws Exception is thrown if an error occurred
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, HandlerInvokeBean handlerInvokeBean) throws Exception {
		LOG.debug("--> HttpDispatcherHandler");
		HandlerInvokeBean invokeBean = handlerInvokeBean;
		if (invokeBean == null) {
			throw new IllegalStateException("Not found invokeBean");
		}
		// dispatch to invoke
		dispatch(invokeBean);
		LOG.debug("finshed");
	}

	@Override
	public void dispatch(HandlerInvokeBean handlerInvokeBean) throws Exception {
		delegate.dispatch(handlerInvokeBean);
	}


	private boolean delegateIsNull() {
		return delegate == null;
	}

	static {
		DEFAULT_DISPATCHER = ((invokeBean) -> {
			invokeBean.handle();
		});
	}
}
