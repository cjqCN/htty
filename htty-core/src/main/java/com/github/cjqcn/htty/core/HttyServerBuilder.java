package com.github.cjqcn.htty.core;

import com.github.cjqcn.htty.core.common.BasicExceptionHandler;
import com.github.cjqcn.htty.core.common.ExceptionHandler;
import com.github.cjqcn.htty.core.common.SSLHandlerFactory;
import com.github.cjqcn.htty.core.interceptor.HttyInterceptor;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.cors.CorsConfig;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class HttyServerBuilder {

	private static final int AVAILABLE_PROCESSORS_NUM = Math.max(1, Runtime.getRuntime().availableProcessors());
	private static final int DEFAULT_BOSS_THREAD_POOL_SIZE = 1;
	private static final int DEFAULT_WORKER_THREAD_POOL_SIZE = AVAILABLE_PROCESSORS_NUM * 2;
	private static final int DEFAULT_CONNECTION_BACKLOG = 1000;
	private static final String DEFAULT_SERVER_HOST = "localhost";
	private static final int DEFAULT_SERVER_PORT = 8640;

	/**
	 * 150M
	 */
	private static final int DEFAULT_HTTP_CHUNK_LIMIT = 150 * 1024 * 1024;

	private final String serverName;
	private final Map<ChannelOption, Object> channelConfigs;
	private final Map<ChannelOption, Object> childChannelConfigs;

	private Collection<HttyWorker> httyWorkers;
	private Collection<HttyInterceptor> httyInterceptors;
	private int bossThreadPoolSize;
	private int workerThreadPoolSize;
	private String host;
	private int port;
	private int httpChunkLimit;
	private SSLHandlerFactory sslHandlerFactory;
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
		httpChunkLimit = DEFAULT_HTTP_CHUNK_LIMIT;
		host = DEFAULT_SERVER_HOST;
		port = DEFAULT_SERVER_PORT;
		httyWorkers = new ArrayList<>();
		httyInterceptors = new ArrayList<>();
		channelConfigs = new HashMap<>();
		childChannelConfigs = new HashMap<>();
		channelConfigs.put(ChannelOption.SO_BACKLOG, DEFAULT_CONNECTION_BACKLOG);
		sslHandlerFactory = null;
		corsConfig = null;
		exceptionHandler = new BasicExceptionHandler();
	}

	public HttyServerBuilder addHttyHandler(HttyWorker... httyWorkers) {
		if (httyWorkers != null) {
			for (HttyWorker httyHandler : httyWorkers) {
				this.httyWorkers.add(httyHandler);
			}
		}
		return this;
	}

	public HttyServerBuilder addHttyHandler(Collection<? extends HttyWorker> httyWorkers) {
		this.httyWorkers.addAll(httyWorkers);
		return this;
	}


	public HttyServerBuilder addHttyInterceptor(HttyInterceptor... httyInterceptors) {
		if (httyInterceptors != null) {
			for (HttyInterceptor httyInterceptor : httyInterceptors) {
				this.httyInterceptors.add(httyInterceptor);
			}
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

	public HttyServerBuilder setHost(String host) {
		this.host = host;
		return this;
	}

	public HttyServerBuilder setPort(int port) {
		this.port = port;
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
		InetSocketAddress bindAddress = new InetSocketAddress(host, port);
		return new BasicHttyServer(serverName, bossThreadPoolSize, workerThreadPoolSize,
				channelConfigs, childChannelConfigs, httyWorkers,
				httyInterceptors, httpChunkLimit, exceptionHandler,
				sslHandlerFactory, corsConfig, bindAddress);
	}


}
