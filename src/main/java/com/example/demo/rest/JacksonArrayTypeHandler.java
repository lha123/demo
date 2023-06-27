package com.example.demo.rest;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Type;

/**
 * Jackson 实现 JSON 字段类型处理器
 *
 * @author hubin
 * @since 2019-08-25
 */
@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JacksonArrayTypeHandler extends AbstractJsonTypeHandler<Object> {
    private Type type;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public JacksonArrayTypeHandler(Class<?> type) {
        if (log.isTraceEnabled()) {
            log.trace("JacksonTypeHandler(" + type + ")");
        }
        Assert.notNull(type, "Type argument cannot be null");

        this.type = type;
    }

    @Override
    protected Object parse(String json) {

        try {
            JavaType javaType = objectMapper.constructType(GenericTypeResolver.resolveType(type, (Class<?>) null));
            ObjectReader objectReader = objectMapper.reader().forType(javaType);
            return objectReader.readValue(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

