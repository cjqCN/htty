package com.github.cjqcn.htty.sample.annotation;

import com.github.cjqcn.htty.annotation.support.handler.DefaultWorkBuildHelper;
import com.github.cjqcn.htty.core.HttyServerBuilder;
import com.github.cjqcn.htty.core.worker.HttyWorker;

import java.util.Collection;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:20
 **/
public class AnnotationServer {

	public static void main(String[] args) throws Exception {
		Collection<HttyWorker> httyWorkers = new DefaultWorkBuildHelper()
				.scanAndBuild("com.github.cjqcn.htty.sample.annotation.work");
		HttyServerBuilder.builder("AnnotationServer")
				.setPort(8080)
				.addHttyHandler(httyWorkers)
				.build().start();
	}

}
