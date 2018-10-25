package com.github.cjqcn.htty.annotation.support;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttyCookie {
    String name();
}
