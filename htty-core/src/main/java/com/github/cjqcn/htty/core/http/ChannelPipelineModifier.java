package com.github.cjqcn.htty.core.http;

import io.netty.channel.ChannelPipeline;

public abstract class ChannelPipelineModifier {

	public abstract void modify(ChannelPipeline pipeline);
}
