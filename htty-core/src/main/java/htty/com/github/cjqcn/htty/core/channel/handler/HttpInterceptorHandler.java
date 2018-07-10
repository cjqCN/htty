package htty.com.github.cjqcn.htty.core.channel.handler;

import htty.com.github.cjqcn.htty.core.abs.HttpContext;
import htty.com.github.cjqcn.htty.core.abs.HttpInterceptor;
import htty.com.github.cjqcn.htty.core.abs.HttpRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * @author jqChan
 * @date 2018/7/10
 */
public class HttpInterceptorHandler extends SimpleChannelInboundHandler<HttpContext> implements HttpInterceptor {


    private static final Logger LOG = LoggerFactory.getLogger(HttpRouterHandler.class);

    private HttpInterceptor delegate;

    public HttpInterceptorHandler(HttpInterceptor delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean preHandle(HttpContext httpContext, Object handler) throws Exception {
        if (delegateIsNull()) {
            return true;
        }
        return delegate.preHandle(httpContext, handler);
    }

    @Override
    public void postHandle(HttpContext httpContext, Object handler, Object msg) throws Exception {
        if (!delegateIsNull()) {
            delegate.postHandle(httpContext, handler, msg);
        }
    }

    @Override
    public void afterCompletion(HttpContext httpContext, Object handler, Exception ex) throws Exception {
        if (!delegateIsNull()) {
            delegate.afterCompletion(httpContext, handler, ex);
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpContext httpContext) throws Exception {

    }

    private boolean delegateIsNull() {
        return delegate == null;
    }
}
