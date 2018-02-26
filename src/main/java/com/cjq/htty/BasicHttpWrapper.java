package com.cjq.htty;

import com.cjq.htty.abs.HttpRequester;
import com.cjq.htty.abs.HttpResponder;
import com.cjq.htty.abs.HttpWrapper;

public class BasicHttpWrapper implements HttpWrapper {

    private HttpRequester httpRequester;
    private HttpResponder httpResponder;

    public BasicHttpWrapper(final HttpRequester httpRequester,
                            final HttpResponder httpResponder) {
        this.httpRequester = httpRequester;
        this.httpResponder = httpResponder;
    }

    @Override
    public HttpRequester getHttpRequester() {
        return httpRequester;
    }

    @Override
    public HttpResponder getHttpResponder() {
        return httpResponder;
    }
}
