package com.cjq.htty.core;

import io.netty.channel.ChannelPipeline;

public abstract class ChannelPipelineModifier {

    public abstract void modify(ChannelPipeline pipeline);
}
