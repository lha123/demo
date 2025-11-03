package com.example.demo.annotation;

import com.github.yulichang.toolkit.Constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinMapping {

    String joinType() default Constant.LEFT_JOIN;

    Class<?> joinClass();

    String thisAlias() default "";

    String thisField();

    String joinField();

    String joinAlias() default "";

    String[] select() default {};


}
