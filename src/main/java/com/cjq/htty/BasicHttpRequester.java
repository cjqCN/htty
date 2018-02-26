package com.cjq.htty;

import com.cjq.htty.abs.AbstractHttpRequester;
import io.netty.handler.codec.http.HttpRequest;

public class BasicHttpRequester extends AbstractHttpRequester {
    public BasicHttpRequester(HttpRequest httpRequest) {
        super(httpRequest);
    }
}
