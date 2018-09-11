package htty.com.github.cjqcn.htty.core.interceptor;

import htty.com.github.cjqcn.htty.core.common.HttyContext;
import htty.com.github.cjqcn.htty.core.common.HttyResourceHolder;
import htty.com.github.cjqcn.htty.core.router.BasicHttyRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-11 21:34
 **/
public class BasicHttyInterceptor implements HttyInterceptor {


    private static final Logger LOG = LoggerFactory.getLogger(BasicHttyRouter.class);

    private final HttyResourceHolder httpResourceHolder;
    private Iterable<? extends HttyInterceptor> httyInterceptors;

    public BasicHttyInterceptor(final HttyResourceHolder httpResourceHolder) {
        this.httpResourceHolder = httpResourceHolder;
        this.httyInterceptors = httpResourceHolder.getHttyInterceptors();
    }

    @Override
    public boolean preHandle(HttyContext httyContext) {
        if (!hasHttpInterceptors()) {
            return true;
        }
        for (HttyInterceptor httyInterceptor : httyInterceptors) {
            if (!httyInterceptor.preHandle(httyContext)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttyContext httyContext) {
        if (!hasHttpInterceptors()) {
            return;
        }
        httyInterceptors.forEach(x -> x.postHandle(httyContext));
    }

    private boolean hasHttpInterceptors() {
        if (httyInterceptors != null) {
            return true;
        } else return false;
    }
}
