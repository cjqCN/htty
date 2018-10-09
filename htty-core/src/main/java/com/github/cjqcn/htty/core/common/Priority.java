package com.github.cjqcn.htty.core.common;

/**
 * @author chenjinquan
 */
public interface Priority {

	/**
	 * 优先级
	 * 数值越小优先级越高
	 * priority < 0 为系统优先级
	 * 建议使用 priority > 0
	 */
	int getPriority();

}
