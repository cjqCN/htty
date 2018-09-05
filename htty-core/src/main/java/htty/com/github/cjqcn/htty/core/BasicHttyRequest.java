package htty.com.github.cjqcn.htty.core;

import htty.com.github.cjqcn.htty.core.abs.AbstractHttyRequest;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

public class BasicHttyRequest extends AbstractHttyRequest {
	public BasicHttyRequest(HttpRequest httpRequest) {
		super(httpRequest);
	}

}
