package com.github.cjqcn.htty.core.worker;

import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;

public interface HttyWorker {

	void handle(HttyRequest httyRequest, HttyResponse httyResponse) throws Exception;

	HttyMethod[] HttpMethod();

	String path();
}
