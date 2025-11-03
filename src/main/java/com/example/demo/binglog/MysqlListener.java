package com.example.demo.binglog;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MysqlListener {
    private BinlogListener listener;
    private String hostName;
    private String database;
    private String table;
    private Class<?> entityClass;

    public MysqlListener(BinlogListener listener, Environment environment) {
        this.listener = listener;
        Class<?> targetClass = AopUtils.getTargetClass(listener);
        Binlog annotation = AnnotationUtils.findAnnotation(targetClass, Binlog.class);
        if (annotation == null) {
            throw new RuntimeException("Mysql binlog listener必须添加 MysqlWatcher @Binlog 注解");
        }
        hostName = environment.resolvePlaceholders(annotation.hostName());
        database = environment.resolvePlaceholders(annotation.database());
        Class aClass = annotation.tableSchemaClass();
        Annotation declaredAnnotation = aClass.getAnnotation(TableName.class);
        if (declaredAnnotation == null) {
            throw new RuntimeException("未配置表异常");
        }
        TableName tableName = (TableName) declaredAnnotation;
        if (StrUtil.isBlank(tableName.value())) {
            table = StrUtil.toUnderlineCase(aClass.getSimpleName());
        } else {
            table = tableName.value();
        }
        entityClass = getGenericClass(targetClass);
    }

    public BinlogListener getListener() {
        return listener;
    }

    public String getHostName() {
        return hostName;
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    private Class<?> getGenericClass(Class<?> targetClass) {
        if (targetClass == Object.class)
            return null;

        Type[] types = targetClass.getGenericInterfaces();
        if (types.length == 0) {
            types = new Type[]{targetClass.getGenericSuperclass()};
        }

        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;
                Type[] array = t.getActualTypeArguments();
                return (Class<?>) array[0];
            }
        }

        return getGenericClass(targetClass.getSuperclass());
    }
}
