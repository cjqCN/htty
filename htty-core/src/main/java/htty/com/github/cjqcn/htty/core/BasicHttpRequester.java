package htty.com.github.cjqcn.htty.core;

import htty.com.github.cjqcn.htty.core.abs.AbstractHttpRequester;
import io.netty.handler.codec.http.HttpRequest;

public class BasicHttpRequester extends AbstractHttpRequester {
	public BasicHttpRequester(HttpRequest httpRequest) {
		super(httpRequest);
	}
}
