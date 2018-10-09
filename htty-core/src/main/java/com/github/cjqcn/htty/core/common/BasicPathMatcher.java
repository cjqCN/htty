package com.github.cjqcn.htty.core.common;


/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-14 17:08
 **/
public class BasicPathMatcher implements PathMatcher {

	public static final PathMatcher instance = new BasicPathMatcher();

	private static final PathUtil pathUtil = new PathUtil();

	@Override
	public boolean match(String pattern, String path) {
		return pathUtil.match(pattern, path) || pathUtil.match(pattern + "?**", path);
	}
}
