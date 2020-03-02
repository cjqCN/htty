package com.github.cjqcn.htty.sample.annotation;

import com.github.cjqcn.htty.annotation.support.handler.DefaultWorkBuildHelper;
import com.github.cjqcn.htty.core.HttyServerBuilder;

/**
 * @author: chenjinquan
 * @create: 2018-09-14 17:20
 **/
public class AnnotationServer {

    public static void main(String[] args) throws Exception {
        HttyServerBuilder.builder("AnnotationServer")
                .setPort(8080)
                .addHandler(DefaultWorkBuildHelper.instance
                        .scanAndBuild("com.github.cjqcn.htty.sample.annotation.work"))
                .build().start();
    }

}
