package com.github.cjqcn.htty.core.common;

import com.github.cjqcn.htty.core.http.HttyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-28 11:22
 **/
public class BasicAuditRecorder implements AuditRecorder {

	public BasicAuditRecorder() {
		LOG.info("Init BasicAuditRecorder");
	}

	private static final Logger LOG = LoggerFactory.getLogger(BasicAuditRecorder.class);

	@Override
	public void record(HttyRequest httyRequest) {
	}
}
