package com.github.cjqcn.htty.core;

import com.github.cjqcn.htty.core.common.ExceptionHandler;
import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.common.SSLHandlerFactory;
import com.github.cjqcn.htty.core.http.ChannelPipelineModifier;
import com.github.cjqcn.htty.core.http.HttyHandler;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.cors.CorsConfig;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


public class HttyServerBuilder {

	private static final int AVAILABLE_PROCESSORS_NUM = Math.max(1, Runtime.getRuntime().availableProcessors());
	private static final int DEFAULT_BOSS_THREAD_POOL_SIZE = 1;
	private static final int DEFAULT_WORKER_THREAD_POOL_SIZE = AVAILABLE_PROCESSORS_NUM * 2;
	private static final int DEFAULT_ROUTER_HANDLER_THREAD_POOL_SIZE = 0;
	private static final int DEFAULT_EXEC_HANDLER_THREAD_POOL_SIZE = 0;
	private static final int DEFAULT_CONNECTION_BACKLOG = 1000;
	private static final long DEFAULT_ROUTE_HANDLER_THREAD_KEEP_ALIVE_TIME_SECS = 60 * 60L;
	private static final long DEFAULT_EXEC_HANDLER_THREAD_KEEP_ALIVE_TIME_SECS = 1 * 60L;
	private static final int DEFAULT_SERVER_PORT = 8640;

	/**
	 * Caller runs by default
	 **/
	private static final RejectedExecutionHandler DEFAULT_REJECTED_EXECUTION_HANDLER =
			new ThreadPoolExecutor.CallerRunsPolicy();

	/**
	 * 150M
	 */
	private static final int DEFAULT_HTTP_CHUNK_LIMIT = 150 * 1024 * 1024;

	private final String serverName;
	private final Map<ChannelOption, Object> channelConfigs;
	private final Map<ChannelOption, Object> childChannelConfigs;

	private Collection<HttyHandler> httyHandlers;
	private Collection<HttyInterceptor> httyInterceptors;
	private int bossThreadPoolSize;
	private int workerThreadPoolSize;
	private int routerThreadPoolSize;
	private int execThreadPoolSize;
	private long routerThreadKeepAliveSecs;
	private long execThreadKeepAliveSecs;
	private String host;
	private int port;
	private RejectedExecutionHandler rejectedExecutionHandler;
	private int httpChunkLimit;
	private SSLHandlerFactory sslHandlerFactory;
	private ChannelPipelineModifier pipelineModifier;
	private ExceptionHandler exceptionHandler;
	private CorsConfig corsConfig;

	public static HttyServerBuilder builder(String serverName) {
		return new HttyServerBuilder(serverName);
	}

	/**
	 * Create a buider of {@link HttyServer}
	 *
	 * @param serverName Name of {@link HttyServer}
	 */
	public HttyServerBuilder(String serverName) {
		this.serverName = serverName;
		bossThreadPoolSize = DEFAULT_BOSS_THREAD_POOL_SIZE;
		workerThreadPoolSize = DEFAULT_WORKER_THREAD_POOL_SIZE;
		routerThreadPoolSize = DEFAULT_ROUTER_HANDLER_THREAD_POOL_SIZE;
		execThreadPoolSize = DEFAULT_EXEC_HANDLER_THREAD_POOL_SIZE;
		routerThreadKeepAliveSecs = DEFAULT_ROUTE_HANDLER_THREAD_KEEP_ALIVE_TIME_SECS;
		execThreadKeepAliveSecs = DEFAULT_EXEC_HANDLER_THREAD_KEEP_ALIVE_TIME_SECS;
		rejectedExecutionHandler = DEFAULT_REJECTED_EXECUTION_HANDLER;
		httpChunkLimit = DEFAULT_HTTP_CHUNK_LIMIT;
		port = DEFAULT_SERVER_PORT;
		httyHandlers = new ArrayList<>();
		httyInterceptors = new ArrayList<>();
		channelConfigs = new HashMap<>();
		childChannelConfigs = new HashMap<>();
		channelConfigs.put(ChannelOption.SO_BACKLOG, DEFAULT_CONNECTION_BACKLOG);
		sslHandlerFactory = null;
		corsConfig = null;
		exceptionHandler = new ExceptionHandler() {
			@Override
			public void handle(Exception ex, HttyContext httyContext) throws Exception {
				httyContext.httyResponse().sendStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
			}
		};
	}

	public HttyServerBuilder addHttyHandler(HttyHandler... httyHandlers) {
		if (httyHandlers != null) {
			for (HttyHandler httyHandler : httyHandlers) {
				this.httyHandlers.add(httyHandler);
			}
		}
		return this;
	}

	public HttyServerBuilder addHttyHandler(Collection<? extends HttyHandler> httyHandlers) {
		this.httyHandlers.addAll(httyHandlers);
		return this;
	}


	public HttyServerBuilder addHttyInterceptor(HttyInterceptor... httyInterceptors) {
		if (httyInterceptors != null)
			for (HttyInterceptor httyInterceptor : httyInterceptors) {
				this.httyInterceptors.add(httyInterceptor);
			}
		return this;
	}

	public HttyServerBuilder addHttyInterceptor(Collection<? extends HttyInterceptor> httyInterceptors) {
		this.httyInterceptors.addAll(httyInterceptors);
		return this;
	}


	public HttyServerBuilder setBossThreadPoolSize(int bossThreadPoolSize) {
		this.bossThreadPoolSize = bossThreadPoolSize;
		return this;
	}


	public HttyServerBuilder setWorkerThreadPoolSize(int workerThreadPoolSize) {
		this.workerThreadPoolSize = workerThreadPoolSize;
		return this;
	}

	public HttyServerBuilder setRouterThreadPoolSize(int routerThreadPoolSize) {
		this.routerThreadPoolSize = routerThreadPoolSize;
		return this;
	}

	public HttyServerBuilder setExecThreadPoolSize(int execThreadPoolSize) {
		this.execThreadPoolSize = execThreadPoolSize;
		return this;
	}

	public HttyServerBuilder setRouterThreadKeepAliveSecs(int routerThreadKeepAliveSecs) {
		this.routerThreadKeepAliveSecs = routerThreadKeepAliveSecs;
		return this;
	}


	public HttyServerBuilder setExecThreadKeepAliveSecs(int execThreadKeepAliveSecs) {
		this.execThreadKeepAliveSecs = execThreadKeepAliveSecs;
		return this;
	}


	public HttyServerBuilder setHost(String host) {
		this.host = host;
		return this;
	}

	public HttyServerBuilder setPort(int port) {
		this.port = port;
		return this;
	}

	public HttyServerBuilder setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
		this.rejectedExecutionHandler = rejectedExecutionHandler;
		return this;
	}

	public HttyServerBuilder setHttpChunkLimit(int httpChunkLimit) {
		this.httpChunkLimit = httpChunkLimit;
		return this;
	}

	public HttyServerBuilder setSslHandlerFactory(SSLHandlerFactory sslHandlerFactory) {
		this.sslHandlerFactory = sslHandlerFactory;
		return this;
	}

	public HttyServerBuilder setPipelineModifier(ChannelPipelineModifier pipelineModifier) {
		this.pipelineModifier = pipelineModifier;
		return this;
	}

	public HttyServerBuilder setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		return this;
	}


	public HttyServerBuilder setCorsConfig(CorsConfig corsConfig) {
		this.corsConfig = corsConfig;
		return this;
	}

	/**
	 * Build a {@link HttyServer} with pre-setting
	 *
	 * @return instance of {@link HttyServer}
	 */
	public HttyServer build() {
		InetSocketAddress bindAddress;
		if (host == null) {
			bindAddress = new InetSocketAddress("localhost", port);
		} else {
			bindAddress = new InetSocketAddress(host, port);
		}
		return new BasicHttyServer(serverName, bossThreadPoolSize, workerThreadPoolSize,
				routerThreadPoolSize, execThreadPoolSize, routerThreadKeepAliveSecs,
				execThreadKeepAliveSecs, channelConfigs, childChannelConfigs,
				rejectedExecutionHandler, pipelineModifier, httyHandlers,
				httyInterceptors, httpChunkLimit, exceptionHandler,
				sslHandlerFactory, corsConfig, bindAddress);
	}


}
