package com.example.demo.annotation;

import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 1)
public class AspectDemo {


    /**
     * 在注解的value参数上定义切点，等同于@Pointcut。
     * joinPoint.proceed()执行前的代码，等同于@Before。
     * joinPoint.proceed()执行后的代码，等同于@AfterReturning。
     * joinPoint.proceed()报错后，等同于@AfterThrowing。
     * joinPoint.proceed() finally中的代码，等同于@After。
     *
     * 作者：伊丽莎白2015
     * 链接：https://www.jianshu.com/p/f5a3552f862b
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */

    /**
     * @Before 前置增强
     */
    @Before("@annotation(demoTest)")
    public void before(JoinPoint point,AspectDemoTest demoTest)  {
        System.out.println(point.getTarget());
        System.out.println("前置增强");
        System.out.println(demoTest);
    }

    /**
     * @Before 前置增强
     */
    @After("@annotation(demoTest)")
    public void After(JoinPoint point,AspectDemoTest demoTest)  {
        System.out.println(point.getTarget());
        System.out.println("前置增强");
        System.out.println(demoTest);
    }

    /**
     * @Before 前置增强
     */
    @AfterReturning("@annotation(demoTest)")
    public void AfterReturning(JoinPoint point,AspectDemoTest demoTest)  {
        System.out.println(point.getTarget());
        System.out.println("前置增强");
        System.out.println(demoTest);
    }

    /**
     * @Before 前置增强
     */
    @AfterThrowing(value = "@annotation(demoTest)",throwing = "e")
    public void AfterThrowing(JoinPoint point,AspectDemoTest demoTest,Exception e)  {
        System.out.println(point.getTarget());
        System.out.println("前置增强");
        System.out.println(demoTest);
    }



    @SneakyThrows
    @Around("@annotation(demoTest)")
    public Object doBefore(ProceedingJoinPoint joinPoint,AspectDemoTest demoTest)  {
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
