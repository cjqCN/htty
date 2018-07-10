package htty.com.github.cjqcn.htty.core.abs;

public interface HttpHandler {

    void init(HandlerContext context) throws Exception;

    void destroy(HandlerContext context) throws Exception;
}
