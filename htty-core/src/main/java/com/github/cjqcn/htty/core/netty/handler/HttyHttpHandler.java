package com.github.cjqcn.htty.core.netty.handler;

import com.github.cjqcn.htty.core.common.ExceptionHandler;
import com.github.cjqcn.htty.core.dispatcher.HttyDispatcher;
import com.github.cjqcn.htty.core.http.BasicHttyRequest;
import com.github.cjqcn.htty.core.http.BasicHttyResponse;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import com.github.cjqcn.htty.core.router.HttyRouter;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-28 11:17
 **/
@ChannelHandler.Sharable
public class HttyHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(HttyHttpHandler.class);

    private HttyInterceptor httyInterceptor;
    private HttyRouter httyRouter;
    private HttyDispatcher httyDispatcher;
    private ExceptionHandler exceptionHandler;
    private boolean sslEnabled;

    public HttyHttpHandler(HttyInterceptor httyInterceptor,
                           HttyRouter httyRouter, HttyDispatcher httyDispatcher,
                           ExceptionHandler exceptionHandler, boolean sslEnabled) {
        this.httyInterceptor = httyInterceptor;
        this.httyRouter = httyRouter;
        this.httyDispatcher = httyDispatcher;
        this.exceptionHandler = exceptionHandler;
        this.sslEnabled = sslEnabled;
        verify();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
        HttyRequest request = new BasicHttyRequest(fullHttpRequest);
        HttyResponse response = new BasicHttyResponse(ctx.channel(), sslEnabled);
        httyHandle(request, response);
    }

    public void httyHandle(HttyRequest request, HttyResponse response) {
        try {
            httyHandle0(request, response);
        } catch (Exception ex) {
            exceptionHandler.handle(ex, request, response);
        }
    }

    public void httyHandle0(HttyRequest request, HttyResponse response) throws Exception {
        if (!httyInterceptor.preHandle(request, response)) {
            LOG.debug("preHandle returns false, connection is closed");
            return;
        }
        HttyWorker httyWorker = httyRouter.route(request);
        httyDispatcher.dispatch(httyWorker, request, response);
        httyInterceptor.postHandle(request, response);
    }

    private void verify() {
        if (httyInterceptor == null) {
            throw new NullPointerException("httyInterceptor is null");
        }
        if (httyRouter == null) {
            throw new NullPointerException("httyRouter is null");
        }
        if (httyDispatcher == null) {
            throw new NullPointerException("httyDispatcher is null");
        }
        if (exceptionHandler == null) {
            throw new NullPointerException("exceptionHandler is null");
        }
    }
}
