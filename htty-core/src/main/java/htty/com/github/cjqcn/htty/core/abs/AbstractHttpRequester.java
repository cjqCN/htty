package htty.com.github.cjqcn.htty.core.abs;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

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
		return null;
	}

	@Override
	public HttpRequest setProtocolVersion(HttpVersion version) {
		return null;
	}

	@Override
	public HttpHeaders headers() {
		return null;
	}

	@Override
	public DecoderResult decoderResult() {
		return null;
	}

	@Override
	public void setDecoderResult(DecoderResult result) {

	}
}
