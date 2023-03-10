package com.example.demo.conf;

import cn.hutool.json.JSONUtil;
import io.grpc.internal.JsonUtil;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SwitchAspect {

    @Pointcut("@annotation(com.example.demo.annotation.Switch)")
    public void operationLog() {}


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


}
