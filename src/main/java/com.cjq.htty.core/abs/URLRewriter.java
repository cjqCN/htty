package com.cjq.htty.core.abs;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public interface URLRewriter {

    boolean rewrite(HttpRequest request, HttpResponse response) throws Exception;
}
