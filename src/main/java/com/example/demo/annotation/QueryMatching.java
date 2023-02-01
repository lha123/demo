package com.example.demo.annotation;

import com.example.demo.enums.TypeEnums;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuhonger
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryMatching {

    String alias() default "t";

    String matching() default "";

    TypeEnums type() default TypeEnums.no;

    JoinInfo join() default @JoinInfo;

    
}
