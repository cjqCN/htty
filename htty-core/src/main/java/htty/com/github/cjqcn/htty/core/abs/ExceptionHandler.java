package htty.com.github.cjqcn.htty.core.abs;


public abstract class ExceptionHandler {
	public abstract void handle(Exception ex, HttpContext httpContext) throws Exception;
}
