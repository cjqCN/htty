package com.github.cjqcn.htty.core.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-25 14:39
 **/
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {


	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

	}
}
