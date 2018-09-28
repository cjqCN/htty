package com.github.cjqcn.htty.core.dispatcher;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: chenjinquan
 * @create: 2018-09-11 23:37
 **/
public class BasicHttyDispatcher implements HttyDispatcher {

	private static final Logger LOG = LoggerFactory.getLogger(BasicHttyDispatcher.class);

	public BasicHttyDispatcher(){
		LOG.info("init BasicHttyDispatcher");
	}

	@Override
	public void dispatch(HttyWorker httyWorker, HttyRequest request, HttyResponse response) throws Exception {
		httyWorker.handle(request, response);
	}
}
