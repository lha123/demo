package com.example.demo.annotation;

import com.example.demo.Aop.BizScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({BizScannerRegistrar.class})
public @interface EnableBizMapping {
    String[] bizPackages();
}
