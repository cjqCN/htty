package com.cjq.htty.core.abs;

import io.netty.channel.ChannelPipeline;

public abstract class ChannelPipelineModifier {

    public abstract void modify(ChannelPipeline pipeline);
}
