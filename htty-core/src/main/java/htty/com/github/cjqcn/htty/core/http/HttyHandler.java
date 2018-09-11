package htty.com.github.cjqcn.htty.core.http;

import htty.com.github.cjqcn.htty.core.common.HandlerContext;

public interface HttyHandler {

    default void init(HandlerContext context) throws Exception {
    }

    default void destroy(HandlerContext context) throws Exception {
    }
}
