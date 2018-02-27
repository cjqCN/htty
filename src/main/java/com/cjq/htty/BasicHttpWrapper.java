package com.cjq.htty;

import com.cjq.htty.core.HttpRequester;
import com.cjq.htty.core.HttpResponder;
import com.cjq.htty.core.HttpWrapper;

public class BasicHttpWrapper implements HttpWrapper {

    private final HttpRequester httpRequester;
    private final HttpResponder httpResponder;

    public BasicHttpWrapper(final HttpRequester httpRequester,
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
