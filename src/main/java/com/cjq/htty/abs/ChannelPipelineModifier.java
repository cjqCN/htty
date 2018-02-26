package com.cjq.htty.abs;

import io.netty.channel.ChannelPipeline;

public abstract class ChannelPipelineModifier {

    public abstract void modify(ChannelPipeline pipeline);
}
