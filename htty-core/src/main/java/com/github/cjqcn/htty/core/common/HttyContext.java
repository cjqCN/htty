package com.github.cjqcn.htty.core.common;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import io.netty.util.concurrent.FastThreadLocal;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-08 22:06
 **/
public class HttyContext {

	public static final String REQUEST_FIELD_NAME = "httyRequestThreadLocal";
	public static final String RESPONSE_FIELD_NAME = "httyResponseThreadLocal";

	private static FastThreadLocal<HttyRequest> httyRequestThreadLocal = new FastThreadLocal<>();
	private static FastThreadLocal<HttyResponse> httyResponseThreadLocal = new FastThreadLocal<>();

	private HttyContext() {
	}

	public static HttyRequest httyRequest() {
		return httyRequestThreadLocal.get();
	}

	public static HttyResponse httyResponse() {
		return httyResponseThreadLocal.get();
	}
}
