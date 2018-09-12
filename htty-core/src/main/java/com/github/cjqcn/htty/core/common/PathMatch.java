package com.github.cjqcn.htty.core.common;

import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.worker.HttyWorker;

/**
 * @description: Path Match
 * @author: chenjinquan
 * @create: 2018-09-11 23:07
 **/
public interface PathMatch {

    boolean mathes(HttyRequest httyRequest, HttyWorker httyWorker);


}
