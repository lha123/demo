package com.example.demo.mybatisConfiguration;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.DateOnlyTypeHandler;
import org.apache.ibatis.type.TimeOnlyTypeHandler;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class SqlPrintConfig {


    @Data
    public static class SqlParameter{
        private String sql;

        private String id;
    }

    public static void main(String[] args) {
        String  spliStr = "WHERE".toLowerCase();
        if(spliStr.contains("where")){
            System.out.println("aa");
        }
    }


    public static SqlParameter replaceParametersExc(BoundSql boundSql,MappedStatement mappedStatement){
        SqlParameter sqlParameter = new SqlParameter();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        String sql = format(boundSql.getSql());
        StringBuilder builder = new StringBuilder();
        int index = 0;
        StringTokenizer tokenizer = new StringTokenizer(sql, "?");
        boolean hasMoreTokens = tokenizer.hasMoreTokens();
        while (hasMoreTokens) {
            String  spliStr = tokenizer.nextToken().toLowerCase();
            builder.append(spliStr);
            hasMoreTokens = tokenizer.hasMoreTokens();
            if (hasMoreTokens) {
                Object o = setParameter(builder, mappedStatement, boundSql, parameterObject, parameterMappingList.get(index));
                if(spliStr.contains("where")){
                    sqlParameter.setId(StrUtil.toString(o));
                }
            }
            index++;
        }
        sqlParameter.setSql(builder.toString());
        return sqlParameter;
    }

    /**
     * 替换参数
     *
     * @param target sql脚本对象
     * @return 返回完整sql
     */
    public static String replaceParameters(StatementHandler target) {
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = target.getBoundSql();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        String sql = format(boundSql.getSql());
        StringBuilder builder = new StringBuilder();
        int index = 0;
        StringTokenizer tokenizer = new StringTokenizer(sql, "?");
        boolean hasMoreTokens = tokenizer.hasMoreTokens();
        while (hasMoreTokens) {
            builder.append(tokenizer.nextToken());
            hasMoreTokens = tokenizer.hasMoreTokens();
            if (hasMoreTokens) {
                setParameter(builder, mappedStatement, boundSql, parameterObject, parameterMappingList.get(index));
            }
            index++;
        }
        return builder.toString();
    }

    /**
     * 格式化原始sql
     *
     * @param original 原始sql
     * @return 返回格式化后sql
     */
    private static String format(String original) {
        StringBuilder builder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(original);
        while (tokenizer.hasMoreTokens()) {
            builder.append(tokenizer.nextToken());
            builder.append(' ');
        }
        return builder.toString();
    }

    /**
     * 设置参数
     *
     * @param sqlBuilder       sql构建器
     * @param mappedStatement  映射对象
     * @param boundSql         sql绑定对象
     * @param parameterObject  参数对象
     * @param parameterMapping 参数映射对象
     */
    private static Object setParameter(
            StringBuilder sqlBuilder,
            MappedStatement mappedStatement,
            BoundSql boundSql,
            Object parameterObject,
            ParameterMapping parameterMapping
    ) {
        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMapping.getMode() != ParameterMode.OUT) {
            Object value;
            String propertyName = parameterMapping.getProperty();
            if (boundSql.hasAdditionalParameter(propertyName)) {
                value = boundSql.getAdditionalParameter(propertyName);
            } else if (parameterObject == null) {
                value = null;
            } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                value = parameterObject;
            } else {
                value = configuration.newMetaObject(parameterObject).getValue(propertyName);
            }
            Object newValue = ParameterHandler.getValue(value, parameterMapping.getTypeHandler());
            sqlBuilder.append(newValue);
            return newValue;
        }
        return null;
    }

    /**
     * 参数助手
     */
    private static class ParameterHandler {
        /**
         * 日期时间格式化
         */
        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        /**
         * 日期格式化
         */
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        /**
         * 时间格式化
         */
        private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

        /**
         * 获取参数值
         *
         * @param value       参数值
         * @param typeHandler 类型助手
         * @return 返回参数值
         */
        static Object getValue(Object value, TypeHandler<?> typeHandler) {
            if (value == null) {
                return null;
            } else if (value instanceof String) {
                return "'" + value + "'";
            } else if (value instanceof Date) {
                return "'" + dateFormat((Date) value, typeHandler) + "'";
            } else {
                return value;
            }
        }

        /**
         * 日期格式化
         *
         * @param date        日期对象
         * @param typeHandler 类型助手
         * @return 返回格式化后字符串
         */
        static String dateFormat(Date date, TypeHandler<?> typeHandler) {
            LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (typeHandler instanceof DateOnlyTypeHandler) {
                return localDateTime.format(DATE_FORMATTER);
            }
            if (typeHandler instanceof TimeOnlyTypeHandler) {
                return localDateTime.format(TIME_FORMATTER);
            }
            return localDateTime.format(DATE_TIME_FORMATTER);
        }
    }
}
