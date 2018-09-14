/**
 * 该包下放置ChannelHandler
 * 主要handler有：
 * AuditHandler -> HttyWrappedHandler  -> HttyInterceptorHandler-> HttyRouterHandler -> HttyDispatcherHandler
 * 分别是审计、Http封装、Http拦截器、Http路由Handler(分配处理器)、请求处理
 */
package com.github.cjqcn.htty.core.netty.handler;