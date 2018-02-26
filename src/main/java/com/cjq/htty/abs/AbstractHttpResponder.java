package com.cjq.htty.abs;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public abstract class AbstractHttpResponder implements HttpResponder {

    private final HttpResponse httpResponse;

    public AbstractHttpResponder(final HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    @Override
    public HttpResponseStatus status() {
        return httpResponse.status();
    }

    @Override
    public HttpResponse setStatus(HttpResponseStatus status) {
        return httpResponse.setStatus(status);
    }

    @Override
    public HttpVersion protocolVersion() {
        return httpResponse.protocolVersion();
    }

    @Override
    public HttpResponse setProtocolVersion(HttpVersion version) {
        return httpResponse.setProtocolVersion(version);
    }

    @Override
    public HttpHeaders headers() {
        return httpResponse.headers();
    }

    @Override
    public DecoderResult decoderResult() {
        return httpResponse.decoderResult();
    }

    @Override
    public void setDecoderResult(DecoderResult result) {
        httpResponse.setDecoderResult(result);
    }
}
