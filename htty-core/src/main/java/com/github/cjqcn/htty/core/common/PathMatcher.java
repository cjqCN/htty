package com.github.cjqcn.htty.core.common;


/**
 * @description: Path Match
 * @author: chenjinquan
 * @create: 2018-09-11 23:07
 **/
public interface PathMatcher {
	boolean match(String pattern, String path);
}
