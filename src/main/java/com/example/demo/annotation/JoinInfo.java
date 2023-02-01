package com.example.demo.annotation;


import com.github.yulichang.toolkit.Constant;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @author liuhonger
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinInfo {

    String[] select() default {};

    String joinType() default Constant.INNER_JOIN;

    String[] on() default {};

}
