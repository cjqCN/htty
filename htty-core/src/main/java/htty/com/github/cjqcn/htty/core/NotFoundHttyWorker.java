package htty.com.github.cjqcn.htty.core;

import htty.com.github.cjqcn.htty.core.abs.HttyWorker;
import htty.com.github.cjqcn.htty.core.abs.HttyRequest;
import htty.com.github.cjqcn.htty.core.abs.HttyResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

public class NotFoundHttyWorker implements HttyWorker {
    @Override
    public void handle(HttyRequest httyRequest, HttyResponse httyResponse) throws Exception {
        httyResponse.sendStatus(HttpResponseStatus.NOT_FOUND);
    }
}
