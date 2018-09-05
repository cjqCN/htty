package htty.com.github.cjqcn.htty.core.abs;

public interface HttpRouter {

	HandlerInvokeBean route(HttpContext httpContext) throws Exception;
}
