package com.github.cjqcn.htty.core;

import com.github.cjqcn.htty.core.common.*;
import com.github.cjqcn.htty.core.http.ChannelPipelineModifier;
import com.github.cjqcn.htty.core.http.HttyHandler;
import com.github.cjqcn.htty.core.interceptor.BasicHttyInterceptor;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import com.github.cjqcn.htty.core.netty.handler.*;
import com.github.cjqcn.htty.core.router.BasicHttyRouter;
import com.github.cjqcn.htty.core.router.HttyRouter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class BasicHttyServer implements HttyServer {

	private static final Logger LOG = LoggerFactory.getLogger(BasicHttyServer.class);

	private final static String SSL_HANDLER_NAMRE = "ssl";
	private final static String HTTP_SERVER_CODEC_HANDLER_NAME = "serverCodec";
	private final static String HTTP_AGGREGATOR_CODEC_HANDLER_NAME = "HttpObjectAggregator";
	private final static String CORS_HANDLER_NAME = "cors";
	private final static String CONTENT_COMPRESSOR_HANDLER_NAME = "compressor";
	private final static String AUDITER_HANDLER_NAME = "auditer";
	private final static String CHUNKED_WRITE_HANDLER = "chunkedWriter";
	private final static String HTTP_WRAPPED_HANDLER = "httpWrapped";
	private final static String ROUTER_HANDLER_NAME = "router";
	private final static String INTERCEPTOR_HANDLER_NAME = "interceptor";
	private final static String DISPATCHER_HANDLER_NAME = "dispatcher";

	private final String serverName;
	private final int bossThreadPoolSize;
	private final int workerThreadPoolSize;
	private final int routerThreadPoolSize;
	private final int execThreadPoolSize;
	private final long routerThreadKeepAliveSecs;
	private final long execThreadKeepAliveSecs;
	private final Map<ChannelOption, Object> channelConfigs;
	private final Map<ChannelOption, Object> childChannelConfigs;
	private final RejectedExecutionHandler rejectedExecutionHandler;
	private final HandlerContext handlerContext;
	private final HttyResourceHolder resourceHandler;
	private final HttyInterceptor httyInterceptor;
	private final HttyRouter httyRouter;
	private final ChannelPipelineModifier pipelineModifier;
	private final int httpChunkLimit;
	private final ExceptionHandler exceptionHandler;
	private final SSLHandlerFactory sslHandlerFactory;
	private final CorsConfig corsConfig;

	private volatile State state;
	private ServerBootstrap bootstrap;
	private ChannelGroup channelGroup;
	private EventExecutorGroup routerEventExecutorGroup;
	private EventExecutorGroup execEventExecutorGroup;
	private InetSocketAddress bindAddress;

	/**
	 * @param serverName
	 * @param bossThreadPoolSize
	 * @param workerThreadPoolSize
	 * @param routerThreadPoolSize
	 * @param execThreadPoolSize
	 * @param routerThreadKeepAliveSecs
	 * @param execThreadKeepAliveSecs
	 * @param channelConfigs
	 * @param childChannelConfigs
	 * @param rejectedExecutionHandler
	 * @param pipelineModifier
	 * @param httpHandlers
	 * @param httpInterceptors
	 * @param httpChunkLimit
	 * @param exceptionHandler
	 * @param sslHandlerFactory
	 * @param corsConfig
	 * @param bindAddress
	 */
	BasicHttyServer(final String serverName, final int bossThreadPoolSize, final int workerThreadPoolSize,
					final int routerThreadPoolSize, final int execThreadPoolSize, final long routerThreadKeepAliveSecs,
					final long execThreadKeepAliveSecs, final Map<ChannelOption, Object> channelConfigs,
					final Map<ChannelOption, Object> childChannelConfigs, final RejectedExecutionHandler
							rejectedExecutionHandler,
					final ChannelPipelineModifier pipelineModifier,
					Iterable<? extends HttyHandler> httpHandlers,
					Iterable<? extends HttyInterceptor> httpInterceptors,
					final int httpChunkLimit, final ExceptionHandler exceptionHandler,
					final SSLHandlerFactory sslHandlerFactory, final CorsConfig corsConfig,
					final InetSocketAddress bindAddress) {
		this.serverName = serverName;
		this.bossThreadPoolSize = bossThreadPoolSize;
		this.workerThreadPoolSize = workerThreadPoolSize;
		this.routerThreadPoolSize = routerThreadPoolSize;
		this.execThreadPoolSize = execThreadPoolSize;
		this.routerThreadKeepAliveSecs = routerThreadKeepAliveSecs;
		this.execThreadKeepAliveSecs = execThreadKeepAliveSecs;
		this.channelConfigs = channelConfigs;
		this.childChannelConfigs = childChannelConfigs;
		this.rejectedExecutionHandler = rejectedExecutionHandler;
		this.resourceHandler = new BasicHttyResourceHolder(httpHandlers, httpInterceptors);
		this.httyInterceptor = new BasicHttyInterceptor(resourceHandler);
		this.httyRouter = new BasicHttyRouter(resourceHandler);
		this.handlerContext = new BasicHandlerContext(resourceHandler);
		this.pipelineModifier = pipelineModifier;
		this.httpChunkLimit = httpChunkLimit;
		this.exceptionHandler = exceptionHandler;
		this.sslHandlerFactory = sslHandlerFactory;
		this.corsConfig = corsConfig;
		this.bindAddress = bindAddress;
		this.state = State.ALREADY;
	}

	@Override
	public synchronized void start() throws Exception {
		if (state == State.ALREADY) {
			try {
				LOG.info("Starting HTTP Service {} at address {}", serverName, bindAddress);
				channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
				resourceHandler.init(handlerContext);
				routerEventExecutorGroup = createEventExecutorGroup(routerThreadPoolSize,
						routerThreadKeepAliveSecs, "router", rejectedExecutionHandler);
				execEventExecutorGroup = createEventExecutorGroup(execThreadPoolSize,
						execThreadKeepAliveSecs, "exec", rejectedExecutionHandler);
				bootstrap = createBootstrap(channelGroup);
				Channel serverChannel = bootstrap.bind(bindAddress).sync().channel();
				channelGroup.add(serverChannel);

				bindAddress = (InetSocketAddress) serverChannel.localAddress();

				LOG.info("Started HTTP Service {} at address {}", serverName, bindAddress);
				state = State.RUNNING;
				//sync
				serverChannel.closeFuture().sync();
			} catch (Throwable t) {
				// Release resources if there is any failure
				channelGroup.close().awaitUninterruptibly();
				try {
					if (bootstrap != null) {
						shutdownExecutorGroups(0, 5, TimeUnit.SECONDS, bootstrap.config().group(),
								bootstrap.config().childGroup(), routerEventExecutorGroup, execEventExecutorGroup);
					} else {
						shutdownExecutorGroups(0, 5, TimeUnit.SECONDS,
								routerEventExecutorGroup, execEventExecutorGroup);
					}
				} catch (Throwable t2) {
					t.addSuppressed(t2);
				}
				state = State.FAILED;
				throw t;
			}
		}
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
	}


	@Override
	public synchronized void stop() throws Exception {
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
							bootstrap.config().group(), bootstrap.config().childGroup(), routerEventExecutorGroup,
							execEventExecutorGroup);
				} finally {
					resourceHandler.destroy(handlerContext);
				}
			}
		} catch (Throwable t) {
			state = State.FAILED;
			throw t;
		}
		state = State.STOPPED;
		LOG.info("Stopped HTTP Service {} on address {}", serverName, bindAddress);
	}


	private EventExecutorGroup createEventExecutorGroup(int size, long threadKeepAliveSecs, String executorName,
														RejectedExecutionHandler rejectedExecutionHandler) {
		if (size <= 0) {
			return null;
		}
		ThreadFactory threadFactory = new ThreadFactory() {
			private final ThreadGroup threadGroup = new ThreadGroup(serverName + "-" + executorName + "-thread");
			private final AtomicLong count = new AtomicLong(0);

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(threadGroup, r, String.format("%s-" + executorName + "-%d", serverName, count
						.getAndIncrement()));
				t.setDaemon(true);
				return t;
			}
		};
		return new DefaultEventExecutorGroup(size, threadFactory);
	}

	/**
	 * Creates the server bootstrap.
	 */
	private ServerBootstrap createBootstrap(final ChannelGroup channelGroup) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(bossThreadPoolSize,
				createDaemonThreadFactory(serverName + "-boss-thread"));
		EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreadPoolSize,
				createDaemonThreadFactory(serverName + "-worker-thread"));
		ServerBootstrap bootstrap = new ServerBootstrap();

		HttpServerCodec httpServerCodec = new HttpServerCodec();
		HttpObjectAggregator httpObjectAggregator = new HttpObjectAggregator(10 * 1024 * 1024);
		CorsHandler corsHandler = null;
		if (corsConfig != null) {
			corsHandler = new CorsHandler(corsConfig);
		}
		AuditHandler auditHandler = new AuditHandler();
		HttpContentCompressor httpContentCompressor = new HttpContentCompressor();
		ChunkedWriteHandler chunkedWriteHandler = new ChunkedWriteHandler();
		HttyWrappedHandler httyWrappedHandler = new HttyWrappedHandler();
		HttyInterceptorHandler httyInterceptorHandler = new HttyInterceptorHandler(httyInterceptor,
				exceptionHandler);
		HttyRouterHandler httyRouterHandler = new HttyRouterHandler(httyRouter);
		HttyDispatcherHandler httyDispatcherHandler = new HttyDispatcherHandler(exceptionHandler);

		CorsHandler _corsHandler = corsHandler;
		bootstrap
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						channelGroup.add(ch);

						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(AUDITER_HANDLER_NAME, auditHandler);

						if (sslHandlerFactory != null) {
							// Add SSLHandler if SSL is enabled
							pipeline.addLast(SSL_HANDLER_NAMRE, sslHandlerFactory.create(ch.alloc()));
						}
						pipeline.addLast(HTTP_SERVER_CODEC_HANDLER_NAME, httpServerCodec);
						pipeline.addLast(HTTP_AGGREGATOR_CODEC_HANDLER_NAME, httpObjectAggregator);
						if (_corsHandler != null) {
							pipeline.addLast(CORS_HANDLER_NAME, _corsHandler);
						}
						pipeline.addLast(CONTENT_COMPRESSOR_HANDLER_NAME, httpContentCompressor);
						pipeline.addLast(CHUNKED_WRITE_HANDLER, chunkedWriteHandler);
						pipeline.addLast(HTTP_WRAPPED_HANDLER, httyWrappedHandler);
						pipeline.addLast(INTERCEPTOR_HANDLER_NAME, httyInterceptorHandler);

						addLast(pipeline, routerEventExecutorGroup, ROUTER_HANDLER_NAME, httyRouterHandler);

						addLast(pipeline, execEventExecutorGroup, DISPATCHER_HANDLER_NAME, httyDispatcherHandler);

						if (pipelineModifier != null) {
							pipelineModifier.modify(pipeline);
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

	private ThreadFactory createDaemonThreadFactory(final String nameFormat) {
		return new ThreadFactory() {
			private final AtomicInteger count = new AtomicInteger();

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName(String.format(nameFormat, count.getAndIncrement()));
				t.setDaemon(true);
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


	private ChannelPipeline addLast(ChannelPipeline pipeline, EventExecutorGroup group,
									String handlerName, ChannelHandler handler) {
		if (group == null) {
			pipeline.addLast(handlerName, handler);
		} else {
			pipeline.addLast(group, handlerName, handler);
		}
		return pipeline;
	}

	public enum State {
		ALREADY,
		RUNNING,
		STOPPED,
		FAILED
	}
}
