package com.github.cjqcn.htty.core.dispatcher;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.worker.HttyWorker;

public interface HttyDispatcher {
	void dispatch(HttyWorker httyWorker, HttyRequest request, HttyResponse response) throws Exception;
}
