package com.cjq.htty;

import com.cjq.htty.core.HandlerInvokeBean;
import com.cjq.htty.core.HttpRequester;
import com.cjq.htty.core.HttpResponder;
import io.netty.handler.codec.http.HttpResponseStatus;

public class NotFoundHandlerInvokeBean implements HandlerInvokeBean {

    private final HttpRequester httpRequester;
    private final HttpResponder httpResponder;

    public NotFoundHandlerInvokeBean(final HttpRequester httpRequester,
                                     final HttpResponder httpResponder) {
        this.httpRequester = httpRequester;
        this.httpResponder = httpResponder;
    }

    @Override
    public void handle() throws Exception {
        httpResponder.sendStatus(HttpResponseStatus.NOT_FOUND);
    }

}
