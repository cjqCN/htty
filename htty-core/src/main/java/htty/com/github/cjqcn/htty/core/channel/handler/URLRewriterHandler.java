package htty.com.github.cjqcn.htty.core.channel.handler;

import htty.com.github.cjqcn.htty.core.abs.HttpContext;
import htty.com.github.cjqcn.htty.core.abs.URLRewriter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLRewriterHandler extends SimpleChannelInboundHandler<HttpContext> implements URLRewriter {

	private static final Logger LOG = LoggerFactory.getLogger(URLRewriterHandler.class);
	private static final URLRewriter DEFUALT_URI_REWRITER = null;

	private final URLRewriter urlRewriter;

	public URLRewriterHandler() {
		this(DEFUALT_URI_REWRITER);
	}

	public URLRewriterHandler(final URLRewriter urlRewriter) {
		this.urlRewriter = urlRewriter;
	}

	/**
	 * Is called for each message of type {@link HttpContext}.
	 *
	 * @param ctx         the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
	 *                    belongs to
	 * @param httpContext the message to handle
	 * @throws Exception is thrown if an error occurred
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, HttpContext httpContext) throws Exception {
		LOG.debug("--> URLRewriterHandler");
		HttpContext wrapper = httpContext;
		if (!rewrite(httpContext)) {
			return;
		}
		ctx.fireChannelRead(httpContext);
	}

	@Override
	public boolean rewrite(HttpContext httpContext) throws Exception {
		if (urlRewriter != null) {
			return urlRewriter.rewrite(httpContext);
		}
		return true;
	}
}
