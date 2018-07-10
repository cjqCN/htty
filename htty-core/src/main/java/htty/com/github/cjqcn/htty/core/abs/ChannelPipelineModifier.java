package htty.com.github.cjqcn.htty.core.abs;

import io.netty.channel.ChannelPipeline;

public abstract class ChannelPipelineModifier {

    public abstract void modify(ChannelPipeline pipeline);
}
