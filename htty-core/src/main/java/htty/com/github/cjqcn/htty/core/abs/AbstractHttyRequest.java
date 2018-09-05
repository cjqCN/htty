package htty.com.github.cjqcn.htty.core.abs;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

public abstract class AbstractHttyRequest implements HttyRequest {

    private final HttpRequest httpRequest;

    public AbstractHttyRequest(final HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpMethod getMethod() {
        return httpRequest.method();
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
    public String getUri() {
        return httpRequest.uri();
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
    public HttpVersion getProtocolVersion() {
        return httpRequest.protocolVersion();
    }

    @Override
    public HttpVersion protocolVersion() {
        return httpRequest.protocolVersion();
    }

    @Override
    public HttpRequest setProtocolVersion(HttpVersion version) {
        return null;
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

    @Override
    public DecoderResult getDecoderResult() {
        return httpRequest.decoderResult();
    }
}
