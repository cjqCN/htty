package com.cjq.htty.core;

import com.cjq.htty.core.abs.ChannelPipelineModifier;
import com.cjq.htty.core.abs.HandlerContext;
import com.cjq.htty.core.abs.HttpResourceHandler;
import com.cjq.htty.core.abs.HttpServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;

@Slf4j
class BasicHttpServer implements HttpServer {

    private final String serverName;
    private final int bossThreadPoolSize;
    private final int workerThreadPoolSize;
    private final int routerThreadPoolSize;
    private final int execThreadPoolSize;
    private final long execThreadKeepAliveSecs;
    private final Map<ChannelOption, Object> channelConfigs;
    private final Map<ChannelOption, Object> childChannelConfigs;
    private final RejectedExecutionHandler rejectedExecutionHandler;
    private final HandlerContext handlerContext;
    private final HttpResourceHandler resourceHandler;
    private final ChannelPipelineModifier pipelineModifier;
    private final int httpChunkLimit;
    private final SSLHandlerFactory sslHandlerFactory;
    private final CorsConfig corsConfig;

    private volatile State state;
    private ServerBootstrap bootstrap;
    private ChannelGroup channelGroup;
    private EventExecutorGroup eventExecutorGroup;
    private InetSocketAddress bindAddress;


    /**
     * @param serverName
     * @param bossThreadPoolSize
     * @param workerThreadPoolSize
     * @param routerThreadPoolSize
     * @param execThreadPoolSize
     * @param execThreadKeepAliveSecs
     * @param channelConfigs
     * @param childChannelConfigs
     * @param rejectedExecutionHandler
     * @param handlerContext
     * @param resourceHandler
     * @param pipelineModifier
     * @param httpChunkLimit
     * @param sslHandlerFactory
     * @param corsConfig
     */
    public BasicHttpServer(final String serverName, final int bossThreadPoolSize, final int workerThreadPoolSize,
                           final int routerThreadPoolSize, final int execThreadPoolSize, final long execThreadKeepAliveSecs,
                           final Map<ChannelOption, Object> channelConfigs, final Map<ChannelOption, Object> childChannelConfigs,
                           final RejectedExecutionHandler rejectedExecutionHandler, HandlerContext handlerContext,
                           final HttpResourceHandler resourceHandler, final ChannelPipelineModifier pipelineModifier,
                           final int httpChunkLimit, final SSLHandlerFactory sslHandlerFactory, CorsConfig corsConfig,
                           final InetSocketAddress bindAddress) {
        this.serverName = serverName;
        this.bossThreadPoolSize = bossThreadPoolSize;
        this.workerThreadPoolSize = workerThreadPoolSize;
        this.routerThreadPoolSize = routerThreadPoolSize;
        this.execThreadPoolSize = execThreadPoolSize;
        this.execThreadKeepAliveSecs = execThreadKeepAliveSecs;
        this.channelConfigs = channelConfigs;
        this.childChannelConfigs = childChannelConfigs;
        this.rejectedExecutionHandler = rejectedExecutionHandler;
        this.handlerContext = handlerContext;
        this.resourceHandler = resourceHandler;
        this.pipelineModifier = pipelineModifier;
        this.httpChunkLimit = httpChunkLimit;
        this.sslHandlerFactory = sslHandlerFactory;
        this.corsConfig = corsConfig;
        this.bindAddress = bindAddress;
        this.state = State.ALREADY;
    }

    @Override
    public synchronized void start() throws Exception {
        if (state == State.ALREADY) {
            log.info("Starting HTTP Service {} at address {}", serverName, bindAddress);
            channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
            resourceHandler.init(handlerContext);
            eventExecutorGroup = createEventExecutorGroup(execThreadPoolSize);
            bootstrap = createBootstrap(channelGroup);
            Channel serverChannel = bootstrap.bind(bindAddress).sync().channel();
            channelGroup.add(serverChannel);

            bindAddress = (InetSocketAddress) serverChannel.localAddress();

            log.debug("Started HTTP Service {} at address {}", serverName, bindAddress);
            state = State.RUNNING;
            return;
        }
        if (state == State.RUNNING) {
            log.info("Ignore start() call on HTTP service {} since it has already been started.", serverName);
            return;
        }
        if (state == State.STOPPED) {
            throw new IllegalStateException("Cannot start the HTTP service "
                    + serverName + " again since it has been stopped");
        }
        if (state == State.FAILED) {
            throw new IllegalStateException("Cannot start the HTTP service "
                    + serverName + " because it was failed earlier");
        }
    }



    @Override
    public void stop() throws Exception {

    }

    private EventExecutorGroup createEventExecutorGroup(int execThreadPoolSize) {
        return null;
    }


    private ServerBootstrap createBootstrap(ChannelGroup channelGroup) {
        return null;
    }


    public enum State {
        ALREADY,
        RUNNING,
        STOPPED,
        FAILED
    }
}
