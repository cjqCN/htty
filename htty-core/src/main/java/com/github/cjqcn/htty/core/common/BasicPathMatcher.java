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

    private static final PathUtil pathUtil = new PathUtil();

    @Override
    public boolean mathes(HttyRequest httyRequest, HttyWorker httyWorker) {
        return pathUtil.match(httyWorker.path(), httyRequest.uri()) || pathUtil.match(httyWorker.path() + "?**", httyRequest.uri());
    }
}
