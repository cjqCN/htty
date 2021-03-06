package com.github.cjqcn.htty.core.interceptor;


import com.github.cjqcn.htty.core.common.PathMatcher;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.interceptor.adapter.HttyInterceptorAdapter;
import com.github.cjqcn.htty.core.interceptor.adapter.HttyInterceptorAdapterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class BasicHttyInterceptorContainer implements HttyInterceptorContainer {

    private static final Logger LOG = LoggerFactory.getLogger(BasicHttyInterceptorContainer.class);
    private List<HttyInterceptorAdapter> interceptorAdapters = new ArrayList<>();
    private HttyInterceptorAdapter[] interceptorAdaptersArr = new HttyInterceptorAdapter[0];

    public BasicHttyInterceptorContainer() {
        LOG.info("Init BasicInterceptorContainer");
        addInternalInterceptor();
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
        for (HttyInterceptorAdapter httyInterceptor : interceptorAdaptersArr) {
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
        String url = request.uri();
        for (int i = interceptorAdaptersArr.length - 1; i >= 0; i--) {
            if (!urlMatch(interceptorAdaptersArr[i], url)) {
                continue;
            }
            interceptorAdaptersArr[i].postHandle(request, response);
        }
    }

    @Override
    public synchronized void addInterceptor(Collection<HttyInterceptor> interceptors) {
        if (interceptors != null) {
            for (HttyInterceptor httyInterceptor : interceptors) {
                if (!(httyInterceptor instanceof HttyInterceptorAdapter)) {
                    httyInterceptor = HttyInterceptorAdapterBuilder.create(httyInterceptor);
                }
                interceptorAdapters.add((HttyInterceptorAdapter) httyInterceptor);
            }
        }
        interceptorAdapters.sort(Comparator.comparing(HttyInterceptorAdapter::getPriority));
        interceptorAdaptersArr = new HttyInterceptorAdapter[interceptorAdapters.size()];
        interceptorAdapters.toArray(interceptorAdaptersArr);
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
        if (interceptorAdapters != null) {
            return true;
        } else {
            return false;
        }
    }


    private void addInternalInterceptor() {
    }
}
