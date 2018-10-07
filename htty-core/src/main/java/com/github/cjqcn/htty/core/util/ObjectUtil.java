package com.github.cjqcn.htty.core.util;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-07 19:52
 **/
public final class ObjectUtil {


    public static <T> boolean eq(T t1, T t2) {
        if (t1 != null) {
            return t1.equals(t2);
        }
        if (t2 != null) {
            return t2.equals(t1);
        }
        return true;
    }


    public static void notNull(Object o, String errorMsg) {
        if (o == null) {
            if (errorMsg != null)
                throw new NullPointerException(errorMsg);
            else {
                throw new NullPointerException("NullPointerException");
            }
        }
    }


}
