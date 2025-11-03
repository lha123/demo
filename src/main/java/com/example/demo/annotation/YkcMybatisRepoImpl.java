package com.example.demo.annotation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class YkcMybatisRepoImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements YkcMybatisRepo<T> {

    @Override
    public Wrapper<T> getWrapper(Object clazz){
        Field[] fields = ClassUtil.getDeclaredFields(clazz.getClass());
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = ReflectUtil.getFieldValue(clazz, field.getName());
            String fieldName = ReflectUtil.getFieldName(field);
            if("".equals(fieldValue) || ObjectUtil.isNull(fieldValue) || "serialVersionUID".equals(fieldName)){
                continue;
            }
            TableField annotation = field.getAnnotation(TableField.class);
            String condition = null;
            if(annotation != null){
                if(!StrUtil.isBlank(annotation.value())){
                    fieldName = annotation.value();
                    condition = annotation.condition();
                }
                if(!annotation.exist()){
                    continue;
                }
            }
            fieldName = StrUtil.toUnderlineCase(fieldName);
            if(StrUtil.isBlank(condition)){
                wrapper.eq(fieldName,fieldValue);
            }else{
                if(YkcSqlCondition.IN.equals(condition)){
                    List list = (List) fieldValue;
                    wrapper.in(fieldName,list);
                }
                if(YkcSqlCondition.LIKE.equals(condition)){
                    wrapper.like(fieldName,fieldValue);
                }
                if(YkcSqlCondition.GT.equals(condition)){
                    if(fieldValue instanceof Date){
                        fieldValue = DateUtil.beginOfDay((Date) fieldValue);
                    }
                    wrapper.gt(fieldName,fieldValue);
                }
                if(YkcSqlCondition.LT.equals(condition)){
                    if(fieldValue instanceof Date){
                        fieldValue = DateUtil.endOfDay((Date) fieldValue);
                    }
                    wrapper.lt(fieldName,fieldValue);
                }
                if(YkcSqlCondition.GE.equals(condition)){
                    if(fieldValue instanceof Date){
                        fieldValue = DateUtil.beginOfDay((Date) fieldValue);
                    }
                    wrapper.ge(fieldName,fieldValue);
                }
                if(YkcSqlCondition.LE.equals(condition)){
                    if(fieldValue instanceof Date){
                        fieldValue = DateUtil.endOfDay((Date) fieldValue);
                    }
                    wrapper.le(fieldName,fieldValue);
                }
            }
        }
        return wrapper;
    }

    @Override
    public Wrapper<T> getListWrapper(Map<String, Object> linkMap) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if(!CollUtil.isEmpty(linkMap)){
            Map<String, Map<String, String>> rpcCondition = (Map<String, Map<String, String>>) linkMap.get("ykcDataCondition");
            for (Map.Entry<String, Object> entry : linkMap.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                if("ykcDataCondition".equals(fieldName) || ObjectUtil.isNull(fieldValue)){
                    continue;
                }
                Map<String, String> rpcMap = rpcCondition.get(fieldName);
                for (Map.Entry<String, String> rpcEntry : rpcMap.entrySet()) {
                    fieldName = rpcEntry.getKey();
                    String condition = rpcEntry.getValue();
                    if(YkcSqlCondition.EQUAL.equals(condition)){
                        wrapper.eq(fieldName,fieldValue);
                    }
                    if(YkcSqlCondition.IN.equals(condition)){
                        List list = (List) fieldValue;
                        wrapper.in(fieldName,list);
                    }
                    if(YkcSqlCondition.LIKE.equals(condition)){
                        wrapper.like(fieldName,fieldValue);
                    }
                    if(YkcSqlCondition.GT.equals(condition)){
                        if(fieldValue instanceof Date){
                            fieldValue = DateUtil.beginOfDay((Date) fieldValue);
                        }
                        wrapper.gt(fieldName,fieldValue);
                    }
                    if(YkcSqlCondition.LT.equals(condition)){
                        if(fieldValue instanceof Date){
                            fieldValue = DateUtil.endOfDay((Date) fieldValue);
                        }
                        wrapper.lt(fieldName,fieldValue);
                    }
                    if(YkcSqlCondition.GE.equals(condition)){
                        if(fieldValue instanceof Date){
                            fieldValue = DateUtil.beginOfDay((Date) fieldValue);
                        }
                        wrapper.ge(fieldName,fieldValue);
                    }
                    if(YkcSqlCondition.LE.equals(condition)){
                        if(fieldValue instanceof Date){
                            fieldValue = DateUtil.endOfDay((Date) fieldValue);
                        }
                        wrapper.le(fieldName,fieldValue);
                    }
                }
            }
        }
        return wrapper;
    }

}
