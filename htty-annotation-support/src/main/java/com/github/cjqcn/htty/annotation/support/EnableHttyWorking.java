package com.github.cjqcn.htty.annotation.support;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableHttyWorking {
    String path() default "";
}
