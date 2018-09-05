package htty.com.github.cjqcn.htty.core.abs;


public interface HttyInterceptor {

	boolean preHandle(HttyContext httyContext, Object handler)
			throws Exception;

	void postHandle(HttyContext httyContext, Object handler, Object msg)
			throws Exception;

	void afterCompletion(HttyContext httyContext, Object handler, Exception ex)
			throws Exception;

}
