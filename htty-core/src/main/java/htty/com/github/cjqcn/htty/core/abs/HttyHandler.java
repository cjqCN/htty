package htty.com.github.cjqcn.htty.core.abs;

public interface HttyHandler {

    default void init(HandlerContext context) throws Exception {
    }

    default void destroy(HandlerContext context) throws Exception {
    }
}
