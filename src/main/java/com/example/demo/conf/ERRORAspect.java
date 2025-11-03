package com.example.demo.conf;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class ERRORAspect {

    @Pointcut("@annotation(com.example.demo.annotation.Switch)")
    public void operationLog() {}

    private Map<String,Map<String,Object>> mathodMap = new ConcurrentHashMap<>();







    /**
     * 前置处理
     * @return
     */
    @SneakyThrows
    @Around(value = "operationLog()")
    public Object doBefore(ProceedingJoinPoint joinPoint)  {
        Object[] args = joinPoint.getArgs();
        Object aThis = joinPoint.getThis();
        Object target = joinPoint.getTarget();
        Signature signature = joinPoint.getSignature();
        System.out.println("args"+ JSONUtil.toJsonStr(args));
        System.out.println("this"+ JSONUtil.toJsonStr(aThis));
        System.out.println("target"+ JSONUtil.toJsonStr(target));
        System.out.println("target"+ JSONUtil.toJsonStr(signature));
        return joinPoint.proceed();
    }

//    @Before("execution(* com.example.demo.Aop.*ServiceImpl.*(..))")
//    public void before(JoinPoint joinPoint){
//        try {
//            Object[] args = joinPoint.getArgs();
//            if(!ArrayUtil.isEmpty(args)){
//                Map<String,Object> map = new HashMap<>();
//                String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
//                for (int i = 0; i < args.length; i++) {
//                    map.put(argNames[i],args[i]);
//                }
//                Object target = joinPoint.getTarget();
//                String packClassPath = target.getClass().getName().concat(".").concat(joinPoint.getSignature().getName());
//                mathodMap.put(packClassPath,map);
//            }
//        } catch (Throwable e) {
//            log.error("Aop原本方法异常拦截入参报错",e);
//        }
//    }

    @AfterThrowing(pointcut = "execution(* com.example.demo.Aop.*ServiceImpl.*(..))",throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) throws Throwable {
        try {
            Object[] args = joinPoint.getArgs();
            if(!ArrayUtil.isEmpty(args)){
                Map<String,Object> map = new HashMap<>();
                String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
                for (int i = 0; i < args.length; i++) {
                    map.put(argNames[i],args[i]);
                }
                Object target = joinPoint.getTarget();
                String packClassPath = target.getClass().getName().concat("#").concat(joinPoint.getSignature().getName());
                log.error("Aop方法异常拦截:{} ====> param:{} ",packClassPath, JSONUtil.toJsonStr(map));
            }
        } catch (Throwable e) {
            log.error("Aop原本方法异常拦截入参报错",e);
        }
    }



}
