package com.github.cjqcn.htty.core.dispatcher;

import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.worker.HttyWorkerWrapper;

/**
 * @author: chenjinquan
 * @create: 2018-09-11 23:37
 **/
public class BasicHttyDispatcher implements HttyDispatcher {
	@Override
	public void dispatch(HttyWorkerWrapper httyWorkerWrapper) throws Exception {
		HttyContext httyContext = httyWorkerWrapper.httyContext();
		httyWorkerWrapper.httyWorker().handle(httyContext.httyRequest(), httyContext.httyResponse());
	}
}
