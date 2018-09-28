package com.github.cjqcn.htty.core.router;

import com.github.cjqcn.htty.core.common.BasicMethodMatcher;
import com.github.cjqcn.htty.core.common.BasicPathMatcher;
import com.github.cjqcn.htty.core.common.MethodMatcher;
import com.github.cjqcn.htty.core.common.PathMatcher;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import com.github.cjqcn.htty.core.worker.MethodNotSupportHttyWorker;
import com.github.cjqcn.htty.core.worker.NotFoundHttyWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicHttyRouter implements HttyRouter {

	private static final Logger LOG = LoggerFactory.getLogger(BasicHttyRouter.class);

	private Iterable<? extends HttyWorker> httyWorkers;
	private PathMatcher pathMatcher = BasicPathMatcher.instance;
	private MethodMatcher methodMatcher = BasicMethodMatcher.instance;

	private static NotFoundHttyWorker notFoundHttyWorker = new NotFoundHttyWorker();
	private static MethodNotSupportHttyWorker methodNotSupportHttyWorker = new MethodNotSupportHttyWorker();

	public BasicHttyRouter(final Iterable<? extends HttyWorker> httyWorkers) {
		LOG.info("Init BasicHttyRouter");
		this.httyWorkers = httyWorkers;
	}

	@Override
	public HttyWorker route(HttyRequest request) {
		return route0(request);
	}

	private HttyWorker route0(HttyRequest request) {
		if (!hasHttyWorkers()) {
			return notFoundHttyWorker;
		}
		for (HttyWorker httyWorker : httyWorkers) {
			if (pathMatcher.mathes(request, httyWorker)) {
				if (methodMatcher.mathes(request, httyWorker)) {
					return httyWorker;
				} else {
					return methodNotSupportHttyWorker;
				}
			}
		}
		return notFoundHttyWorker;
	}

	private boolean hasHttyWorkers() {
		return httyWorkers != null;
	}
}
