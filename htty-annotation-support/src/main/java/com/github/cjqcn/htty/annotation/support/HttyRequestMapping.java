package com.github.cjqcn.htty.annotation.support;

import com.github.cjqcn.htty.core.http.HttyMethod;

import java.lang.annotation.*;

import static com.github.cjqcn.htty.core.http.HttyMethod.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttyRequestMapping {

    HttyMethod[] HttpMethod() default {GET, POST, HEAD, OPTIONS, DELETE, PUT};

    String path() default "";
}
