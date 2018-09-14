package com.github.cjqcn.htty.core.router;

import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.worker.HttyWorkerWrapper;

public interface HttyRouter {
	HttyWorkerWrapper route(HttyContext httyContext) throws Exception;
}
