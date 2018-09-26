package com.github.cjqcn.htty.annotation.support.handler;

import com.github.cjqcn.htty.core.worker.HttyWorker;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-26 15:23
 **/
public interface WorkBuildHelper {

	Collection<HttyWorker> scanAndBuild(String packageName);

	Collection<HttyWorker> scanAndBuild(Class<?> claz);

	default Collection<HttyWorker> scanAndBuild(Collection<Class<?>> classes) {
		if (classes == null || classes.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		List<HttyWorker> httyWorkers = new LinkedList<>();
		for (Class claz : classes) {
			httyWorkers.addAll(scanAndBuild(claz));
		}
		return httyWorkers;
	}

}
