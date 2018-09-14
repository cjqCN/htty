package com.github.cjqcn.htty.core.common;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.worker.HttyWorker;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:08
 **/
public class BasicPathMatcher implements PathMatcher {

	public static final PathMatcher instance = new BasicPathMatcher();

	//TODO
	@Override
	public boolean mathes(HttyRequest httyRequest, HttyWorker httyWorker) {
		if (httyRequest.uri().equals(httyWorker.path())) {
			return true;
		}
		return false;
	}
}
