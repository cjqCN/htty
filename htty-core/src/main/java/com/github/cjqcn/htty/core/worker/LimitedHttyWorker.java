package com.github.cjqcn.htty.core.worker;

import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-29 22:43
 **/
public class LimitedHttyWorker implements InternalWorker {

    @Override
    public void handle(HttyRequest httyRequest, HttyResponse httyResponse) {
        httyResponse.sendString(HttpResponseStatus.SERVICE_UNAVAILABLE, "Service Unavailable");
    }

    @Override
    public HttyMethod[] HttpMethod() {
        throw new IllegalStateException("Not supported to call");
    }

    @Override
    public String path() {
        throw new IllegalStateException("Not supported to call");
    }


}
