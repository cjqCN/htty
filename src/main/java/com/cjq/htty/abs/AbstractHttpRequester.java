package com.cjq.htty.abs;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;

public abstract class AbstractHttpRequester implements HttpRequester {

    private final HttpRequest httpRequest;

    public AbstractHttpRequester(final HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpMethod method() {
        return httpRequest.method();
    }

    @Override
    public HttpRequest setMethod(HttpMethod method) {
        return httpRequest.setMethod(method);
    }

    @Override
    public String uri() {
        return httpRequest.uri();
    }

    @Override
    public HttpRequest setUri(String uri) {
        return httpRequest.setUri(uri);
    }

    @Override
    public HttpVersion protocolVersion() {
        return httpRequest.protocolVersion();
    }

    @Override
    public HttpRequest setProtocolVersion(HttpVersion version) {
        return httpRequest.setProtocolVersion(version);
    }

    @Override
    public HttpHeaders headers() {
        return httpRequest.headers();
    }

    @Override
    public DecoderResult decoderResult() {
        return httpRequest.decoderResult();
    }

    @Override
    public void setDecoderResult(DecoderResult result) {
        httpRequest.setDecoderResult(result);
    }
}
