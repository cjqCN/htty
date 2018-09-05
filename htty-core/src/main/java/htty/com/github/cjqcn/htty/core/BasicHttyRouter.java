package htty.com.github.cjqcn.htty.core;

import htty.com.github.cjqcn.htty.core.abs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicHttyRouter implements HttyRouter {

    private static final Logger LOG = LoggerFactory.getLogger(BasicHttyRouter.class);

    private final HttyResourceHolder httpResourceHolder;
    private final Iterable<? extends HttyHandler> httpHandlers;
    private final Iterable<? extends HttyInterceptor> httpInterceptors;

    public BasicHttyRouter(final HttyResourceHolder httpResourceHolder) throws Exception {
        this.httpResourceHolder = httpResourceHolder;
        this.httpHandlers = httpResourceHolder.getHttpHandlers();
        this.httpInterceptors = httpResourceHolder.getHttpInterceptors();
    }

    @Override
    public HttyWorker route(HttyContext httyContext) throws Exception {
        if (!checkHasHttpHandlers()) {
            return new NotFoundHttyWorker();
        }
        // TODO
        return new NotFoundHttyWorker();
    }

    private boolean checkHasHttpHandlers() {
        return httpHandlers != null;
    }

}
