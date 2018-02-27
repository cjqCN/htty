package com.cjq.htty.channel.handler;

import com.cjq.htty.core.*;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLRewriterHandler extends ChannelHandlerAdapter implements URLRewriter {

    private static final Logger LOG = LoggerFactory.getLogger(URLRewriterHandler.class);
    private static final URLRewriter DEFUALT_URI_REWRITER = null;

    private final URLRewriter urlRewriter;

    public URLRewriterHandler() {
        this(DEFUALT_URI_REWRITER);
    }

    public URLRewriterHandler(final URLRewriter urlRewriter) {
        this.urlRewriter = urlRewriter;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOG.debug("--> URLRewriterHandler");
        if (msg instanceof HttpWrapper) {
            HttpWrapper wrapper = (HttpWrapper) msg;
            if (!rewrite(wrapper.httpRequester(), wrapper.httpResponder()))
                return;
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public boolean rewrite(HttpRequester requester, HttpResponder responder) throws Exception {
        if (urlRewriter != null) {
            return urlRewriter.rewrite(requester, responder);
        }
        return true;
    }
}
