package com.example.demo.mybatisConfiguration;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;


/**
 *
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisSqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            log.info("==>      MySQL: {}", SqlPrintConfig.replaceParameters((StatementHandler)invocation.getTarget()));
        } catch (Exception e) {
            log.info("the sql is error !!!", e);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(this.getRealHandler(target), this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @SneakyThrows
    private Object getRealHandler(Object target) {
        Class<?> clz = target.getClass();
        if (clz.getTypeName().contains("$")) {
            Field field = target.getClass().getSuperclass().getDeclaredField("h");
            field.setAccessible(true);
            Object obj = field.get(target);
            field = obj.getClass().getDeclaredField("target");
            field.setAccessible(true);
            return field.get(obj);
        } else {
            return target;
        }
    }


}
