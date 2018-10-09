package com.github.cjqcn.htty.core.interceptor;


import com.github.cjqcn.htty.core.common.PathMatcher;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.interceptor.adapter.HttyInterceptorAdapter;
import com.github.cjqcn.htty.core.interceptor.adapter.HttyInterceptorAdapterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class BasicHttyInterceptorContainer implements HttyInterceptorContainer {

	private static final Logger LOG = LoggerFactory.getLogger(BasicHttyInterceptorContainer.class);
	private List<HttyInterceptorAdapter> httyInterceptorAdapters = new LinkedList<>();
	private HttyInterceptorAdapter[] httyInterceptorAdaptersArr = new HttyInterceptorAdapter[0];

	public BasicHttyInterceptorContainer() {
		LOG.info("Init BasicHttyInterceptorContainer");
	}

	public BasicHttyInterceptorContainer(Collection<HttyInterceptor> httyInterceptors) {
		this();
		addInterceptor(httyInterceptors);
	}

	@Override
	public boolean preHandle(HttyRequest request, HttyResponse response) {
		if (!hasHttpInterceptors()) {
			return true;
		}
		String url = request.uri();
		for (HttyInterceptorAdapter httyInterceptor : httyInterceptorAdaptersArr) {
			if (!urlMatch(httyInterceptor, url)) {
				continue;
			}
			if (!httyInterceptor.preHandle(request, response)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttyRequest request, HttyResponse response) {
		if (!hasHttpInterceptors()) {
			return;
		}
		// 逆序
		for (int i = httyInterceptorAdaptersArr.length - 1; i >= 0; i--) {
			httyInterceptorAdaptersArr[i].postHandle(request, response);
		}
	}

	@Override
	public synchronized void addInterceptor(Collection<HttyInterceptor> httyInterceptors) {
		if (httyInterceptors != null) {
			for (HttyInterceptor httyInterceptor : httyInterceptors) {
				if (!(httyInterceptor instanceof HttyInterceptorAdapter)) {
					httyInterceptor = HttyInterceptorAdapterBuilder.create(httyInterceptor);
				}
				httyInterceptorAdapters.add((HttyInterceptorAdapter) httyInterceptor);
			}
		}
		fix();
	}

	private void fix() {
		httyInterceptorAdapters.sort(Comparator.comparing(HttyInterceptorAdapter::getPriority));
		httyInterceptorAdaptersArr = new HttyInterceptorAdapter[httyInterceptorAdapters.size()];
		httyInterceptorAdapters.toArray(httyInterceptorAdaptersArr);
	}


	private boolean urlMatch(HttyInterceptorAdapter interceptorAdapter, String url) {
		Collection<String> includeUrlPatterns = interceptorAdapter.getIncludeUrlPatterns();
		Collection<String> excludeUrlPatterns = interceptorAdapter.getExcludeUrlPatterns();
		PathMatcher pathMatcher = interceptorAdapter.getPathMatcher();
		for (String urlPattern : excludeUrlPatterns) {
			if (pathMatcher.match(urlPattern, url)) {
				return false;
			}
		}
		for (String urlPattern : includeUrlPatterns) {
			if (pathMatcher.match(urlPattern, url)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasHttpInterceptors() {
		if (httyInterceptorAdapters != null) {
			return true;
		} else {
			return false;
		}
	}
}
