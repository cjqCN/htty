package com.github.cjqcn.htty.core.common;

import com.github.cjqcn.htty.core.http.HttyRequest;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-28 11:20
 **/
public interface AuditRecorder {
	void record(HttyRequest httyRequest);
}
