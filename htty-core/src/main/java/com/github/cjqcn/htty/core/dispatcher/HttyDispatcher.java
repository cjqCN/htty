package com.github.cjqcn.htty.core.dispatcher;

import com.github.cjqcn.htty.core.worker.HttyWorkerWrapper;

public interface HttyDispatcher {
	void dispatch(HttyWorkerWrapper httyWorkerWrapper) throws Exception;
}
