package com.cjq.htty;

import com.cjq.htty.abs.AbstractHttpResponder;
import io.netty.handler.codec.http.HttpResponse;

public class BasicHttpResponder extends AbstractHttpResponder {

    public BasicHttpResponder(HttpResponse httpResponse) {
        super(httpResponse);
    }
}
