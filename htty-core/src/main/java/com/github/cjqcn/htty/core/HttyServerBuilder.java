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

import static java.lang.Thread.*;


public class HttyServerBuilder {

    private static final int AVAILABLE_PROCESSORS_NUM = Math.max(1, Runtime.getRuntime().availableProcessors());
    private static final int DEFAULT_BOSS_THREAD_POOL_SIZE = 1;
    private static final int DEFAULT_WORKER_THREAD_POOL_SIZE = AVAILABLE_PROCESSORS_NUM * 2;
    private static final int DEFAULT_CONNECTION_BACKLOG = 1024;
    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int DEFAULT_SERVER_PORT = 8640;
    private static final boolean DEFAULT_SHUTDOWN_HOOK = true;

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
    private int businessThreadPoolSize;
    private String host;
    private int port;
    private int httpChunkLimit;
    private SSLHandlerFactory sslHandlerFactory;
    private ExceptionHandler exceptionHandler;
    private CorsConfig corsConfig;
    private int priority;
    private boolean shutdownHook = DEFAULT_SHUTDOWN_HOOK;

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
        this.priority = NORM_PRIORITY;
        this.bossThreadPoolSize = DEFAULT_BOSS_THREAD_POOL_SIZE;
        this.workerThreadPoolSize = DEFAULT_WORKER_THREAD_POOL_SIZE;
        this.httpChunkLimit = DEFAULT_HTTP_CHUNK_LIMIT;
        this.businessThreadPoolSize = 0;
        this.host = DEFAULT_SERVER_HOST;
        this.port = DEFAULT_SERVER_PORT;
        this.httyWorkers = new ArrayList<>();
        this.httyInterceptors = new ArrayList<>();
        this.channelConfigs = new HashMap<>();
        this.childChannelConfigs = new HashMap<>();
        this.channelConfigs.put(ChannelOption.SO_BACKLOG, DEFAULT_CONNECTION_BACKLOG);
        this.sslHandlerFactory = null;
        this.corsConfig = null;
        this.exceptionHandler = new BasicExceptionHandler();
    }

    /**
     * The higher the value, the higher the priority
     *
     * @param priority
     * @return
     */
    public HttyServerBuilder setPriority(int priority) {
        if (priority > MAX_PRIORITY || priority < MIN_PRIORITY) {
            throw new IllegalArgumentException();
        }
        this.priority = priority;
        return this;
    }

    public HttyServerBuilder addHandler(HttyWorker... httyWorkers) {
        if (httyWorkers != null) {
            for (HttyWorker httyHandler : httyWorkers) {
                this.httyWorkers.add(httyHandler);
            }
        }
        return this;
    }

    public HttyServerBuilder addHandler(Collection<? extends HttyWorker> httyWorkers) {
        this.httyWorkers.addAll(httyWorkers);
        return this;
    }


    public HttyServerBuilder addInterceptor(HttyInterceptor... httyInterceptors) {
        if (httyInterceptors != null) {
            for (HttyInterceptor httyInterceptor : httyInterceptors) {
                this.httyInterceptors.add(httyInterceptor);
            }
        }
        return this;
    }

    public HttyServerBuilder addInterceptor(Collection<? extends HttyInterceptor> httyInterceptors) {
        this.httyInterceptors.addAll(httyInterceptors);
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

    public HttyServerBuilder setShutdownHook(boolean shutdownHook) {
        this.shutdownHook = shutdownHook;
        return this;
    }

    public HttyServerBuilder setBusinessThreadPoolSize(int businessThreadPoolSize) {
        this.businessThreadPoolSize = businessThreadPoolSize;
        return this;
    }


    /**
     * Build a {@link HttyServer} with pre-setting
     *
     * @return instance of {@link HttyServer}
     */
    public HttyServer build() {
        InetSocketAddress bindAddress = new InetSocketAddress(host, port);
        return new BasicHttyServer(serverName, priority, bossThreadPoolSize,
                workerThreadPoolSize, businessThreadPoolSize, channelConfigs, childChannelConfigs,
                httyWorkers, httyInterceptors, httpChunkLimit, exceptionHandler,
                sslHandlerFactory, corsConfig, bindAddress, shutdownHook);
    }


}
