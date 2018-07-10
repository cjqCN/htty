package com.cjq.htty;

import com.cjq.htty.core.HttpRequester;
import com.cjq.htty.core.HttpResponder;
import com.cjq.htty.core.HttpContext;

public class BasicHttpContext implements HttpContext {

    private final HttpRequester httpRequester;
    private final HttpResponder httpResponder;

    public BasicHttpContext(final HttpRequester httpRequester,
                            final HttpResponder httpResponder) {
        this.httpRequester = httpRequester;
        this.httpResponder = httpResponder;
    }

    @Override
    public HttpRequester httpRequester() {
        return httpRequester;
    }

    @Override
    public HttpResponder httpResponder() {
        return httpResponder;
    }
}
