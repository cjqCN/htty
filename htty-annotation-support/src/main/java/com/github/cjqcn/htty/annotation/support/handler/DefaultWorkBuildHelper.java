package com.github.cjqcn.htty.annotation.support.handler;

import com.github.cjqcn.htty.annotation.support.EnableHttyWorking;
import com.github.cjqcn.htty.annotation.support.HttyRequestMapping;
import com.github.cjqcn.htty.annotation.support.util.ClassUtil;
import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;
import com.github.cjqcn.htty.core.worker.HttyWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-26 15:30
 **/
public class DefaultWorkBuildHelper implements WorkBuildHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultWorkBuildHelper.class);

    @Override
    public Collection<HttyWorker> scanAndBuild(String packageName) {
        try {
            List<Class<?>> classes = ClassUtil.getClassList(packageName);
            return scanAndBuild(classes);
        } catch (Exception e) {
            LOG.error("", e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Collection<HttyWorker> scanAndBuild(Class<?> claz) {
        if (claz == null || !claz.isAnnotationPresent(EnableHttyWorking.class)) {
            return Collections.EMPTY_LIST;
        }
        EnableHttyWorking enableHttyWorking = claz.getAnnotation(EnableHttyWorking.class);
        final String prefixPath = enableHttyWorking.prefixPath();
        Map<Method, HttyRequestMapping> methodHttyRequestMappingMap = getHasHttyRequestMappingMethod(claz);
        if (methodHttyRequestMappingMap.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        Object instance;
        try {
            instance = claz.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            LOG.warn("Class:{} fail to instance", claz.getName(), ex);
            return Collections.EMPTY_LIST;
        }
        List<HttyWorker> httyWorkers = new LinkedList<>();
        Object _Instance = instance;
        methodHttyRequestMappingMap.forEach((k, v) -> {
            HttyWorker httyWorker;
            if ((httyWorker = createHttyWorker(k, v, _Instance, prefixPath)) != null) {
                httyWorkers.add(httyWorker);
            }
        });
        return httyWorkers;
    }


    private HttyWorker createHttyWorker(Method method, HttyRequestMapping httyRequestMapping, Object instance,
                                        String prefixPath) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length != 2) {
            LOG.warn("method:{} fail to registerd, because its parameters' length is not 2", method.getName());
            return null;
        }
        int tag = 0;
        if (parameters[0].getType().equals(HttyRequest.class) && parameters[1].getType().equals(HttyResponse.class)) {
            tag = 1;
        }
        if (parameters[0].getType().equals(HttyResponse.class) && parameters[1].getType().equals(HttyRequest.class)) {
            tag = 2;
        }
        if (tag == 0) {
            LOG.warn("method:{} fail to registerd, because its parameters' type is invalid", method.getName());
            return null;
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
            public HttyMethod[] HttpMethod() {
                return httyRequestMapping.HttpMethod();
            }

            @Override
            public String path() {
                return prefixPath + httyRequestMapping.path();
            }
        };

    }

    private static Map<Method, HttyRequestMapping> getHasHttyRequestMappingMethod(Class<?> targetClass) {
        Map<Method, HttyRequestMapping> methodPermissionMap = new HashMap<>();
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(HttyRequestMapping.class) != null) {
                methodPermissionMap.put(method, method.getAnnotation(HttyRequestMapping.class));
            }
        }
        return methodPermissionMap;
    }
}
