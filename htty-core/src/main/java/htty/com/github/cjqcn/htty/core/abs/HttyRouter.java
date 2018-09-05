package htty.com.github.cjqcn.htty.core.abs;

public interface HttyRouter {

	HttyWorker route(HttyContext httyContext) throws Exception;
}
