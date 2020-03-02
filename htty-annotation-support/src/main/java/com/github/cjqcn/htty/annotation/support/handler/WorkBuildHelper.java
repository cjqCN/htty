package com.github.cjqcn.htty.annotation.support.handler;

import com.github.cjqcn.htty.core.worker.HttyWorker;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: chenjinquan
 * @create: 2018-09-26 15:23
 **/
public interface WorkBuildHelper {

    List<HttyWorker> scanAndBuild(String packageName);

    List<HttyWorker> scanAndBuild(Class<?> clazz);

    default List<HttyWorker> scanAndBuild(Collection<Class<?>> classes) {
        if (classes == null || classes.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<HttyWorker> workers = new LinkedList<>();
        for (Class clazz : classes) {
            workers.addAll(scanAndBuild(clazz));
        }
        return workers;
    }

}
