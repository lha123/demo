package com.example.demo.Aop;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.example.demo.annotation.BizImplements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态代理，需要注意的是，这里用到的是JDK自带的动态代理，代理对象只能是接口，不能是类
 */
public class BizProxy<T> implements InvocationHandler {

    private Class<T> interfaceType;
    private BeanFactory applicationContext;

    private Map<String, Map<String, String>> mockMap = new ConcurrentHashMap<>();


    private ConcurrentHashMap<Method, MethodHandle> methodHandleMap = new ConcurrentHashMap<>();

    private Random random = new Random(9000);

    public BizProxy(Class<T> it, BeanFactory applicationContext) {
        this.interfaceType = it;
        this.applicationContext = applicationContext;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用前，args = " + args);
        //MockProperties bean1 = applicationContext.getBean(MockProperties.class);
        // todo 如果开启mock
        /*if(bean1.isEnable()){
            for (MockProperties.Api include : bean1.getIncludes()) {
                String api = include.getApi();
                List<String> uris = include.getUris();
            }
        } else */
        BizImplements annotation1 = interfaceType.getAnnotation(BizImplements.class);
        if (annotation1 != null) {
            Class value = annotation1.value();
            Object bean = applicationContext.getBean(value);
            if (bean != null) {
                Method beanMethod = ReflectionUtils.findMethod(bean.getClass(), method.getName(), method.getParameterTypes());
                if (beanMethod != null) {
                    beanMethod.setAccessible(true);
                    return ReflectionUtils.invokeMethod(beanMethod, bean, args);
                }else{
                    if (method.isDefault()) {
                        MethodHandle defaultMethodHandle = methodHandleMap.computeIfAbsent(method, key -> {
                            MethodHandle methodHandle = MethodHandlesUtil.getSpecialMethodHandle(method);
                            return methodHandle.bindTo(proxy);
                        });
                        return defaultMethodHandle.invokeWithArguments(args);
                    }
                }
            }
        }
        return null;
        //return ReflectionUtils.invokeMethod(method, this, args); // 其他非控制器的方法

//        if (annotation != null) {
//            System.out.println(annotation);
//            Object bean = applicationContext.getBean(annotation.beanType());
//            Method beanMethod = ReflectionUtils.findMethod(bean.getClass(), annotation.methodName(), method.getParameterTypes());
//            beanMethod.setAccessible(true);
//            Object result = ReflectionUtils.invokeMethod(beanMethod, bean, args);
//            System.out.println("调用后，result = " + result);
//            return result;
//        } else {
//            return ReflectionUtils.invokeMethod(method, this, args); // 其他非控制器的方法
//        }
    }
}
