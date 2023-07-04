package com.example.demo.annotation;


import cn.hutool.core.util.DesensitizedUtil;
import com.example.demo.po.DictSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DictSerializer.class)
public @interface Sensitive {

    DesensitizedUtil.DesensitizedType value();
}
