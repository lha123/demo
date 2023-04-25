package com.example.demo.Aop;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.demo.annotation.BizImplements;
import com.example.demo.conf.MockProperties;
import com.example.demo.po.DogShow1;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    private AbstractBeanFactory beanFactory;

    private Map<String, Map<String, String>> mockMap = new ConcurrentHashMap<>();


    private ConcurrentHashMap<Method, MethodHandle> methodHandleMap = new ConcurrentHashMap<>();

    private Random random = new Random(9000);

    public BizProxy(Class<T> it, AbstractBeanFactory beanFactory) {
        this.interfaceType = it;
        this.beanFactory = beanFactory;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MockProperties mockProperties = beanFactory.getBean(MockProperties.class);
        if(mockProperties.isEnable()){
            GetMapping get = method.getAnnotation(GetMapping.class);
            PostMapping post = method.getAnnotation(PostMapping.class);
            String s = "";
            if(get != null){
                 s = HttpUtil.get(mockProperties.getUrl()+get.value()[0]);
            }
            if(post != null){
                 s = HttpUtil.post(mockProperties.getUrl()+post.value()[0], "");
            }
            if(JSONUtil.isTypeJSON(s)){
                return JSONUtil.toBean(s, TypeUtil.getReturnType(method),false);
            }
            return resolveAnnotationValue(s,TypeUtil.getReturnClass(method));
        }else{
            BizImplements annotation1 = interfaceType.getAnnotation(BizImplements.class);
            if (annotation1 != null) {
                Class value = annotation1.value();
                Object bean = beanFactory.getBean(value);
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

    private <T> T resolveAnnotationValue(Object value, Class<T> requiredType) {
        if (value == null) {
            return null;
        }
        TypeConverter typeConverter = beanFactory.getTypeConverter();
        if (value instanceof String) {
            String strVal = beanFactory.resolveEmbeddedValue((String) value);
            BeanExpressionResolver beanExpressionResolver = beanFactory.getBeanExpressionResolver();
            if (beanExpressionResolver != null) {
                value = beanExpressionResolver.evaluate(strVal, new BeanExpressionContext(beanFactory, null));
            } else {
                value = strVal;
            }
        }
        try {
            return typeConverter.convertIfNecessary(value, requiredType);
        } catch (TypeMismatchException e) {
            throw new IllegalArgumentException("Failed to convert value of parameter");
        }
    }

}
