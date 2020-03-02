package com.github.cjqcn.htty.core.common;

import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.worker.HttyWorker;

/**
 * @author: chenjinquan
 * @create: 2018-09-14 17:05
 **/
public class BasicMethodMatcher implements MethodMatcher {

    public static final MethodMatcher instance = new BasicMethodMatcher();

    @Override
    public boolean match(HttyRequest httyRequest, HttyWorker httyWorker) {
        HttyMethod httyMethod = httyRequest.method();
        HttyMethod[] supportedMethod = httyWorker.httpMethod();
        if (supportedMethod == null || supportedMethod.length == 0) {
            return false;
        }
        for (HttyMethod i : supportedMethod) {
            if (i == httyMethod) {
                return true;
            }
        }
        return false;
    }
}
