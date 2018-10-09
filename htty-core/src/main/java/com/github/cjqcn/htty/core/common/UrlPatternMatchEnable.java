package com.github.cjqcn.htty.core.common;

import java.util.Collection;

public interface UrlPatternMatchEnable {

	Collection<String> getIncludeUrlPatterns();

	Collection<String> getExcludeUrlPatterns();

	PathMatcher getPathMatcher();

}
