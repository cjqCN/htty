package com.github.cjqcn.htty.core.router;

import com.github.cjqcn.htty.core.common.HttyContext;
import com.github.cjqcn.htty.core.common.HttyResourceHolder;
import com.github.cjqcn.htty.core.common.MethodMatch;
import com.github.cjqcn.htty.core.common.PathMatch;
import com.github.cjqcn.htty.core.http.HttyHandler;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import com.github.cjqcn.htty.core.worker.HttyWorkerWrapper;
import com.github.cjqcn.htty.core.worker.MethodNotSupportHttyWorker;
import com.github.cjqcn.htty.core.worker.NotFoundHttyWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BasicHttyRouter implements HttyRouter {

	private static final Logger LOG = LoggerFactory.getLogger(BasicHttyRouter.class);

	private final HttyResourceHolder httpResourceHolder;
	private Iterable<? extends HttyWorker> httyWorkers;
	private PathMatch pathMatch;
	private MethodMatch methodMatch;

	private static NotFoundHttyWorker notFoundHttyWorker = new NotFoundHttyWorker();
	private static MethodNotSupportHttyWorker methodNotSupportHttyWorker = new MethodNotSupportHttyWorker();

	public BasicHttyRouter(final HttyResourceHolder httpResourceHolder) {
		this.httpResourceHolder = httpResourceHolder;
		initHttyWorker(httpResourceHolder.getHttyHandlers());
	}

	@Override
	public HttyWorkerWrapper route(HttyContext httyContext) {
		return HttyWorkerWrapper.create(_route(httyContext), httyContext);
	}

	private HttyWorker _route(HttyContext httyContext) {
		if (!hasHttyWorkers()) {
			return notFoundHttyWorker;
		}
		for (HttyWorker httyWorker : httyWorkers) {
			if (pathMatch.mathes(httyContext.httyRequest(), httyWorker)) {
				if (methodMatch.mathes(httyContext.httyRequest(), httyWorker)) {
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


	private void initHttyWorker(Iterable<? extends HttyHandler> httyHandlers) {
		List<HttyWorker> _httyWorkers = new ArrayList<>();
		httyHandlers.forEach(x -> {
			if (x instanceof HttyWorker) {
				_httyWorkers.add((HttyWorker) x);
			}
		});
		httyWorkers = _httyWorkers;
	}

}
