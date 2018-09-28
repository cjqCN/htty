package com.github.cjqcn.htty.core.router;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.worker.HttyWorker;

public interface HttyRouter {
	HttyWorker route(HttyRequest request) throws Exception;
}
