package com.github.cjqcn.htty.core.common;


import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.worker.HttyWorker;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:05
 **/
public class BasicMethodMatcher implements MethodMatcher {

	public static final MethodMatcher instance = new BasicMethodMatcher();

	@Override
	public boolean mathes(HttyRequest httyRequest, HttyWorker httyWorker) {
		try {
			HttyMethod httyMethod = httyRequest.method();
			HttyMethod[] supportedHttyMethod = httyWorker.HttpMethod();
			if (supportedHttyMethod != null) {
				for (HttyMethod i : supportedHttyMethod) {
					if (i == httyMethod) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}
}
