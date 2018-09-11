package htty.com.github.cjqcn.htty.core.netty.handler;


import htty.com.github.cjqcn.htty.core.common.BasicHttyContext;
import htty.com.github.cjqcn.htty.core.http.BasicHttyRequest;
import htty.com.github.cjqcn.htty.core.http.BasicHttyResponse;
import htty.com.github.cjqcn.htty.core.http.HttyRequest;
import htty.com.github.cjqcn.htty.core.http.HttyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttyWrappedHandler extends SimpleChannelInboundHandler<HttpRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(HttyWrappedHandler.class);


    /**
     * Is called for each message of type {@link HttpRequest}.
     *
     * @param channelHandlerContext the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                              belongs to
     * @param httpRequest           the httpRequest to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        LOG.debug("init BasicHttyContext");
        HttyRequest requester = new BasicHttyRequest(httpRequest);
        HttyResponse responder = new BasicHttyResponse(channelHandlerContext.channel(), false);
        channelHandlerContext.fireChannelRead(new BasicHttyContext(requester, responder));
    }
}
