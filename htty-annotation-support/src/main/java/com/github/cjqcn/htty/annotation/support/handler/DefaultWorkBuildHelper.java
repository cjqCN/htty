package com.github.cjqcn.htty.annotation.support.handler;

import com.github.cjqcn.htty.annotation.support.EnableHttyWorking;
import com.github.cjqcn.htty.annotation.support.HttyRequestMapping;
import com.github.cjqcn.htty.annotation.support.util.ClassUtil;
import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.worker.HttyWorker;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author: chenjinquan
 * @create: 2018-09-26 15:30
 **/
public class DefaultWorkBuildHelper implements WorkBuildHelper {

    public static final WorkBuildHelper instance = new DefaultWorkBuildHelper();

    private DefaultWorkBuildHelper() {
    }

    @Override
    public List<HttyWorker> scanAndBuild(String packageName) {
        List<Class<?>> classes;
        try {
            classes = ClassUtil.getClassList(packageName);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return scanAndBuild(classes);
    }

    @Override
    public List<HttyWorker> scanAndBuild(Class<?> clazz) {
        try {
            if (clazz == null || !clazz.isAnnotationPresent(EnableHttyWorking.class)) {
                return Collections.EMPTY_LIST;
            }
            EnableHttyWorking enableHttyWorking = clazz.getAnnotation(EnableHttyWorking.class);
            final String prefixPath = enableHttyWorking.path();
            Map<Method, HttyRequestMapping> methodHttyRequestMappingMap = getHasHttyRequestMappingMethod(clazz);
            if (methodHttyRequestMappingMap.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
            final Object instance = clazz.getDeclaredConstructor().newInstance();
            List<HttyWorker> workers = new LinkedList<>();
            methodHttyRequestMappingMap.forEach((k, v) -> {
                HttyWorker httyWorker;
                if ((httyWorker = createHttyWorker(k, v, instance, prefixPath)) != null) {
                    workers.add(httyWorker);
                }
            });
            return workers;
        } catch (Exception ex) {
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            }
            throw new RuntimeException(ex);
        }
    }

    private HttyWorker createHttyWorker(Method method, HttyRequestMapping httyRequestMapping, Object instance, String prefixPath) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length != 2) {
            throw new RuntimeException("method:" + method.getName() + " because it's parameters' length is not 2");
        }
        int tag = 0;
        if (parameters[0].getType().equals(HttyRequest.class) && parameters[1].getType().equals(HttyResponse.class)) {
            tag = 1;
        }
        if (parameters[0].getType().equals(HttyResponse.class) && parameters[1].getType().equals(HttyRequest.class)) {
            tag = 2;
        }
        if (tag == 0) {
            throw new RuntimeException("method:" + method.getName() + " fail to registered, because it's parameters' type is invalid");
        }
        final int _tag = tag;
        return new HttyWorker() {
            @Override
            public void handle(HttyRequest httyRequest, HttyResponse httyResponse) throws Exception {
                try {
                    if (_tag == 1) {
                        method.invoke(instance, httyRequest, httyResponse);
                    } else {
                        method.invoke(instance, httyResponse, httyRequest);
                    }
                } catch (InvocationTargetException ex) {
                    throw (Exception) ex.getTargetException();
                }
            }

            @Override
            public HttyMethod[] httpMethod() {
                return httyRequestMapping.HttpMethod();
            }

            @Override
            public String path() {
                return prefixPath + httyRequestMapping.path();
            }
        };

    }

    private static Map<Method, HttyRequestMapping> getHasHttyRequestMappingMethod(Class<?> targetClass) {
        Map<Method, HttyRequestMapping> map = new HashMap<>();
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(HttyRequestMapping.class) != null) {
                map.put(method, method.getAnnotation(HttyRequestMapping.class));
            }
        }
        return map;
    }
}
