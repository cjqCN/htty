package com.github.cjqcn.htty.core.http;

import io.netty.handler.codec.http.HttpRequest;

public class BasicHttyRequest extends AbstractHttyRequest {
    public BasicHttyRequest(HttpRequest httpRequest) {
        super(httpRequest);
    }

}
