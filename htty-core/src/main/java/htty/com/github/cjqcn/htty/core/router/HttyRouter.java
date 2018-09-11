package htty.com.github.cjqcn.htty.core.router;

import htty.com.github.cjqcn.htty.core.common.HttyContext;
import htty.com.github.cjqcn.htty.core.worker.HttyWorker;

public interface HttyRouter {
    HttyWorker route(HttyContext httyContext) throws Exception;
}
