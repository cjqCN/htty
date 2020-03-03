package com.github.cjqcn.htty.core;

import com.github.cjqcn.htty.core.common.ExceptionHandler;
import com.github.cjqcn.htty.core.common.SSLHandlerFactory;
import com.github.cjqcn.htty.core.dispatcher.BasicHttyDispatcher;
import com.github.cjqcn.htty.core.dispatcher.HttyDispatcher;
import com.github.cjqcn.htty.core.interceptor.BasicHttyInterceptorContainer;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import com.github.cjqcn.htty.core.netty.handler.HttyHttpHandler;
import com.github.cjqcn.htty.core.router.BasicHttyRouter;
import com.github.cjqcn.htty.core.router.HttyRouter;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerKeepAliveHandler;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FastThreadLocalThread;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class BasicHttyServer implements HttyServer {

    private static final Logger LOG = LoggerFactory.getLogger(BasicHttyServer.class);

    private final static String SSL_HANDLER = "ssl";
    private final static String HTTP_SERVER_CODEC_HANDLER = "httpServerCodec";
    private final static String HTTP_AGGREGATOR_CODEC_HANDLER = "HttpObjectAggregator";
    private final static String CORS_HANDLER = "cors";
    private final static String CONTENT_COMPRESSOR_HANDLER = "compressor";
    private final static String KEEP_ALIVE_HANDLER = "keep_alive";
    private final static String HTTP_HANDLER = "httpWorker";
    private final static String CHUNKED_WRITE_HANDLER = "chunkedWriter";

    private final String serverName;
    private final int bossThreadPoolSize;
    private final int workerThreadPoolSize;
    private final int businessThreadPoolSize;
    private final Map<ChannelOption, Object> channelConfigs;
    private final Map<ChannelOption, Object> childChannelConfigs;
    private final HttyInterceptor httyInterceptor;
    private final HttyRouter httyRouter;
    private final HttyDispatcher httyDispatcher;
    private final ExceptionHandler exceptionHandler;

    private final int httpChunkLimit;

    private final SSLHandlerFactory sslHandlerFactory;
    private final CorsConfig corsConfig;

    private volatile State state;
    private ServerBootstrap bootstrap;
    private ChannelGroup channelGroup;
    private InetSocketAddress bindAddress;

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    private NioEventLoopGroup businessGroup;

    private int priority;

    /**
     * @param serverName
     * @param priority
     * @param bossThreadPoolSize
     * @param workerThreadPoolSize
     * @param channelConfigs
     * @param childChannelConfigs
     * @param workers
     * @param httpInterceptors
     * @param httpChunkLimit
     * @param exceptionHandler
     * @param sslHandlerFactory
     * @param corsConfig
     * @param bindAddress
     */
    BasicHttyServer(final String serverName, final int priority,
                    final int bossThreadPoolSize,
                    final int workerThreadPoolSize,
                    final int businessThreadPoolSize,
                    final Map<ChannelOption, Object> channelConfigs,
                    final Map<ChannelOption, Object> childChannelConfigs,
                    Collection<HttyWorker> workers,
                    Collection<HttyInterceptor> httpInterceptors,
                    final int httpChunkLimit, final ExceptionHandler exceptionHandler,
                    final SSLHandlerFactory sslHandlerFactory, final CorsConfig corsConfig,
                    final InetSocketAddress bindAddress,
                    final boolean shutdownHook) {
        this.serverName = serverName;
        this.priority = priority;
        this.bossThreadPoolSize = bossThreadPoolSize;
        this.workerThreadPoolSize = workerThreadPoolSize;
        this.businessThreadPoolSize = businessThreadPoolSize;
        this.channelConfigs = channelConfigs;
        this.childChannelConfigs = childChannelConfigs;
        this.httyInterceptor = new BasicHttyInterceptorContainer(httpInterceptors);
        this.httyRouter = new BasicHttyRouter(workers);
        this.httyDispatcher = new BasicHttyDispatcher();
        this.httpChunkLimit = httpChunkLimit;
        this.exceptionHandler = exceptionHandler;
        this.sslHandlerFactory = sslHandlerFactory;
        this.corsConfig = corsConfig;
        this.bindAddress = bindAddress;
        this.state = State.ALREADY;
        if (shutdownHook) {
            addShutdownHook();
        }
    }

    @Override
    public synchronized void start() throws Exception {
        if (state == State.RUNNING) {
            LOG.info("Ignore start() call on HTTP service {} since it has already been started.", serverName);
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
        try {
            long start = System.currentTimeMillis();
            LOG.info("Starting HTTP Service {} at address {}", serverName, bindAddress);
            channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
            bootstrap = createBootstrap(channelGroup);
            Channel serverChannel = bootstrap.bind(bindAddress).sync().channel();
            channelGroup.add(serverChannel);

            bindAddress = (InetSocketAddress) serverChannel.localAddress();

            long end = System.currentTimeMillis();
            LOG.info("Started HTTP Service {} at address {}, cost:{}ms", serverName, bindAddress, end - start);
            state = State.RUNNING;
        } catch (Throwable t) {
            // Release resources if there is any failure
            channelGroup.close().awaitUninterruptibly();
            try {
                if (bootstrap != null) {
                    shutdownExecutorGroups(0, 5, TimeUnit.SECONDS, bossGroup, workerGroup, businessGroup);
                }
            } catch (Throwable t2) {
                t.addSuppressed(t2);
            }
            state = State.FAILED;
            throw t;
        }
    }


    @Override
    public synchronized void stop() {
        if (state == State.STOPPED) {
            LOG.info("Ignore stop() call on HTTP service {} since it has already been stopped.", serverName);
            return;
        }
        LOG.info("Stopping HTTP Service {}", serverName);
        try {
            try {
                channelGroup.close().awaitUninterruptibly();
            } finally {
                try {
                    shutdownExecutorGroups(0, 5, TimeUnit.SECONDS,
                            bootstrap.config().group(), bootstrap.config().childGroup());
                } finally {
                }
            }
        } catch (Throwable t) {
            state = State.FAILED;
            throw t;
        }
        state = State.STOPPED;
        LOG.info("Stopped HTTP Service {} on address {}", serverName, bindAddress);
    }

    /**
     * Creates the server bootstrap.
     */
    private ServerBootstrap createBootstrap(final ChannelGroup channelGroup) {

        bossGroup = new NioEventLoopGroup(bossThreadPoolSize,
                createThreadFactory(serverName + "-boss-thread-%d", priority));
        LOG.info("Init bossGroup, size:{}", bossGroup.executorCount());

        workerGroup = new NioEventLoopGroup(workerThreadPoolSize,
                createThreadFactory(serverName + "-worker-thread-%d", priority));
        LOG.info("Init workerGroup, size:{}", workerGroup.executorCount());

        NioEventLoopGroup _businessGroup = null;
        if (businessThreadPoolSize > 0) {
            _businessGroup = new NioEventLoopGroup(businessThreadPoolSize,
                    createThreadFactory(serverName + "-business-thread-%d", priority));
            LOG.info("Init businessGroup, size:{}", workerGroup.executorCount());
        }
        businessGroup = _businessGroup;

        ServerBootstrap bootstrap = new ServerBootstrap();

        final boolean sslEnabled = (sslHandlerFactory != null);

        HttyHttpHandler httyHttpHandler = new HttyHttpHandler(httyInterceptor, httyRouter,
                httyDispatcher, exceptionHandler, sslEnabled);

        bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        channelGroup.add(ch);
                        ChannelPipeline pipeline = ch.pipeline();

                        if (sslEnabled) {
                            // Add SSLHandler if SSL is enabled
                            pipeline.addLast(SSL_HANDLER, sslHandlerFactory.create(ch.alloc()));
                        }
                        pipeline.addLast(HTTP_SERVER_CODEC_HANDLER, new HttpServerCodec());
                        pipeline.addLast(HTTP_AGGREGATOR_CODEC_HANDLER,
                                new HttpObjectAggregator(10 * 1024 * 1024));
                        if (corsConfig != null) {
                            pipeline.addLast(CORS_HANDLER, new CorsHandler(corsConfig));
                        }
                        pipeline.addLast(CONTENT_COMPRESSOR_HANDLER, new HttpContentCompressor());
                        pipeline.addLast(CHUNKED_WRITE_HANDLER, new ChunkedWriteHandler());
                        pipeline.addLast(KEEP_ALIVE_HANDLER, new HttpServerKeepAliveHandler());

                        if (businessGroup == null) {
                            pipeline.addLast(HTTP_HANDLER, httyHttpHandler);
                        } else {
                            pipeline.addLast(businessGroup, HTTP_HANDLER, httyHttpHandler);
                        }
                    }
                });

        for (Map.Entry<ChannelOption, Object> entry : channelConfigs.entrySet()) {
            bootstrap.option(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<ChannelOption, Object> entry : childChannelConfigs.entrySet()) {
            bootstrap.childOption(entry.getKey(), entry.getValue());
        }

        return bootstrap;
    }

    @Override
    public void suspend() {
        throw new UnsupportedOperationException("The server does not support pauses.");
    }

    @Override
    public void recover() {
        throw new UnsupportedOperationException("The server does not support recover.");
    }

    private ThreadFactory createThreadFactory(final String nameFormat, int priority) {
        return new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new FastThreadLocalThread(r);
                t.setName(String.format(nameFormat, count.getAndIncrement()));
                t.setPriority(priority);
                return t;
            }
        };
    }

    private void shutdownExecutorGroups(long quietPeriod, long timeout, TimeUnit unit, EventExecutorGroup... groups) {
        Exception ex = null;
        List<Future<?>> futures = new ArrayList<>();
        for (EventExecutorGroup group : groups) {
            if (group == null) {
                continue;
            }
            futures.add(group.shutdownGracefully(quietPeriod, timeout, unit));
        }

        for (Future<?> future : futures) {
            try {
                future.syncUninterruptibly();
            } catch (Exception e) {
                if (ex == null) {
                    ex = e;
                } else {
                    ex.addSuppressed(e);
                }
            }
        }

        if (ex != null) {
            // Just log, don't rethrow since it shouldn't happen normally and
            // there is nothing much can be done from the caller side
            LOG.warn("Exception raised when shutting down executor", ex);
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                stop(), serverName + " shutdownHook"
        ));
    }


    enum State {
        ALREADY,
        RUNNING,
        STOPPED,
        FAILED
    }
}
