package htty.com.github.cjqcn.htty.core.abs;

public interface URLRewriter {
	/**
	 * @param httpContext
	 * @return
	 * @throws Exception
	 */
	boolean rewrite(HttpContext httpContext) throws Exception;
}
