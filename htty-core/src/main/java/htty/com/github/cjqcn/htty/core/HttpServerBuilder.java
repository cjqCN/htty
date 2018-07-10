package htty.com.github.cjqcn.htty.core;

import htty.com.github.cjqcn.htty.core.abs.*;
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


public class HttpServerBuilder {

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

	private static final int DEFAULT_HTTP_CHUNK_LIMIT = 150 * 1024 * 1024;

	private final String serverName;
	private final Map<ChannelOption, Object> channelConfigs;
	private final Map<ChannelOption, Object> childChannelConfigs;

	private Collection<HttpHandler> httpHandlers;
	private Collection<HttpInterceptor> httpInterceptors;
	private URLRewriter urlRewriter;
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

	public static HttpServerBuilder builder(String serverName) {
		return new HttpServerBuilder(serverName);
	}

	/**
	 * Create a buider of {@link HttpServer}
	 *
	 * @param serverName Name of {@link HttpServer}
	 */
	public HttpServerBuilder(String serverName) {
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
		httpHandlers = new ArrayList<>();
		httpInterceptors = new ArrayList<>();
		channelConfigs = new HashMap<>();
		childChannelConfigs = new HashMap<>();
		channelConfigs.put(ChannelOption.SO_BACKLOG, DEFAULT_CONNECTION_BACKLOG);
		urlRewriter = null;
		sslHandlerFactory = null;
		corsConfig = null;
		exceptionHandler = new ExceptionHandler() {
			@Override
			public void handle(Exception ex, HttpContext httpContext) throws Exception {
				httpContext.httpResponder().sendStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
			}
		};
	}

	public HttpServerBuilder addHttpHandler(HttpHandler... _httpHandlers) {
		if (_httpHandlers != null) {
			for (HttpHandler httpHandler : _httpHandlers) {
				this.httpHandlers.add(httpHandler);
			}
		}
		return this;
	}

	public HttpServerBuilder addHttpHandler(Collection<? extends HttpHandler> _httpHandlers) {
		Collection tmp = _httpHandlers;
		this.httpHandlers.addAll(tmp);
		return this;
	}


	public HttpServerBuilder addHttpInterceptor(HttpInterceptor... _httpInterceptors) {
		if (_httpInterceptors != null)
			for (HttpInterceptor httpInterceptor : _httpInterceptors) {
				this.httpInterceptors.add(httpInterceptor);
			}
		return this;
	}

	public HttpServerBuilder addHttpInterceptor(Collection<? extends HttpInterceptor> _httpInterceptors) {
		Collection tmp = _httpInterceptors;
		this.httpInterceptors.addAll(tmp);
		return this;
	}


	public HttpServerBuilder setBossThreadPoolSize(int bossThreadPoolSize) {
		this.bossThreadPoolSize = bossThreadPoolSize;
		return this;
	}


	public HttpServerBuilder setWorkerThreadPoolSize(int workerThreadPoolSize) {
		this.workerThreadPoolSize = workerThreadPoolSize;
		return this;
	}

	public HttpServerBuilder setRouterThreadPoolSize(int routerThreadPoolSize) {
		this.routerThreadPoolSize = routerThreadPoolSize;
		return this;
	}

	public HttpServerBuilder setExecThreadPoolSize(int execThreadPoolSize) {
		this.execThreadPoolSize = execThreadPoolSize;
		return this;
	}

	public HttpServerBuilder setRouterThreadKeepAliveSecs(int routerThreadKeepAliveSecs) {
		this.routerThreadKeepAliveSecs = routerThreadKeepAliveSecs;
		return this;
	}


	public HttpServerBuilder setExecThreadKeepAliveSecs(int execThreadKeepAliveSecs) {
		this.execThreadKeepAliveSecs = execThreadKeepAliveSecs;
		return this;
	}


	public HttpServerBuilder setHost(String host) {
		this.host = host;
		return this;
	}

	public HttpServerBuilder setPort(int port) {
		this.port = port;
		return this;
	}

	public HttpServerBuilder setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
		this.rejectedExecutionHandler = rejectedExecutionHandler;
		return this;
	}

	public HttpServerBuilder setHttpChunkLimit(int httpChunkLimit) {
		this.httpChunkLimit = httpChunkLimit;
		return this;
	}

	public HttpServerBuilder setSslHandlerFactory(SSLHandlerFactory sslHandlerFactory) {
		this.sslHandlerFactory = sslHandlerFactory;
		return this;
	}

	public HttpServerBuilder setPipelineModifier(ChannelPipelineModifier pipelineModifier) {
		this.pipelineModifier = pipelineModifier;
		return this;
	}

	public HttpServerBuilder setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	public HttpServerBuilder setUrlRewriter(URLRewriter urlRewriter) {
		this.urlRewriter = urlRewriter;
		return this;
	}

	public HttpServerBuilder setCorsConfig(CorsConfig corsConfig) {
		this.corsConfig = corsConfig;
		return this;
	}

	/**
	 * Build a {@link HttpServer} with pre-setting
	 *
	 * @return instance of {@link HttpServer}
	 */
	public HttpServer build() {
		InetSocketAddress bindAddress;
		if (host == null) {
			bindAddress = new InetSocketAddress("localhost", port);
		} else {
			bindAddress = new InetSocketAddress(host, port);
		}
		return new BasicHttpServer(serverName, bossThreadPoolSize, workerThreadPoolSize,
				routerThreadPoolSize, execThreadPoolSize, routerThreadKeepAliveSecs,
				execThreadKeepAliveSecs, channelConfigs, childChannelConfigs,
				rejectedExecutionHandler, pipelineModifier, httpHandlers,
				httpInterceptors, httpChunkLimit, exceptionHandler,
				sslHandlerFactory, corsConfig, bindAddress, urlRewriter);
	}


}
