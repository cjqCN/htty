package htty.com.github.cjqcn.htty.core.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditHandler extends ChannelInboundHandlerAdapter {


    private static final Logger LOG = LoggerFactory.getLogger(AuditHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String res = buf.toString(CharsetUtil.UTF_8);
        LOG.debug(res);
        ctx.fireChannelRead(msg);
    }

}
