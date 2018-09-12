package com.github.cjqcn.htty.core.common;


import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;

public class BasicHttyContext implements HttyContext {

    private final HttyRequest httyRequest;
    private final HttyResponse httyResponse;

    public BasicHttyContext(final HttyRequest httyRequest,
                            final HttyResponse httyResponse) {
        this.httyRequest = httyRequest;
        this.httyResponse = httyResponse;
    }

    @Override
    public HttyRequest httyRequest() {
        return httyRequest;
    }

    @Override
    public HttyResponse httyResponse() {
        return httyResponse;
    }
}
