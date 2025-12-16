package com.example.demo.mybatisConfiguration;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.text.StrJoiner;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author miemie
 * @since 3.4.0
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Slf4j
public class MybatisLogPlusInterceptor implements Interceptor {



    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        try {
            return invocation.proceed();
        }finally {
            CompletableFuture.runAsync(()->{
                Object[] args = invocation.getArgs();
                MappedStatement ms = (MappedStatement) args[0];
                Object parameter = args[1];

                // 获取SQL信息
                BoundSql boundSql = ms.getBoundSql(parameter);
                String sql = boundSql.getSql();
                String tableName = parseTableName(sql, ms.getSqlCommandType());
                SqlPrintConfig.SqlParameter sqlParameter = SqlPrintConfig.replaceParametersExc(boundSql, ms);
                StrJoiner builder = new StrJoiner("\n");
                builder.append(ms.getId());
                for (StackTraceElement stackTraceElement : stackTrace) {
                    String className = stackTraceElement.getClassName();
                    if(!className.contains("MybatisLogPlusInterceptor")){
                        if((className.contains("com.example.demo") && !className.contains("$"))){
                            builder.append(StrFormatter.format("{}.{}({})",className,stackTraceElement.getMethodName(),stackTraceElement.getFileName()+":"+stackTraceElement.getLineNumber()));
                        }
                    }
                }
                log.info("\nsql-->{}:{} \nStackTrace-->{}",tableName+"_"+sqlParameter.getId(),sqlParameter.getSql(),builder);
            });
        }

    }


    private String parseTableName(String sql, SqlCommandType commandType) {
        // 根据不同的SQL类型解析表名
        Pattern pattern;
        switch (commandType) {
            case SELECT:
            case DELETE:
                pattern = Pattern.compile("(?i)from\\s+(\\w+)");
                break;
            case UPDATE:
                pattern = Pattern.compile("(?i)update\\s+(\\w+)");
                break;
            case INSERT:
                pattern = Pattern.compile("(?i)into\\s+(\\w+)");
                break;
            default:
                return "unknown";
        }

        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "unknown";
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
