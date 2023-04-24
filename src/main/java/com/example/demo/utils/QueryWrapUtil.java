package com.example.demo.utils;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.annotation.OrderByIsAsc;
import com.example.demo.annotation.OrderGroup;
import com.example.demo.annotation.QueryMatching;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liuhonger 待完善
 */
public class QueryWrapUtil {



    //---------数据转换-------------
    public static <T,R,K,V> Map<K,V> toMapWithObj(T clazz,BaseMapper<R> baseMapper,Function<R,K> keyFunc,Function<R,V> valueFunc){
        List<R> list = selectList(clazz, baseMapper);
        return CollStreamUtil.toMap(list,keyFunc, valueFunc);
    }

    public static<T,R,K,V> Map<K, List<V>> toMapWithGroup(T clazz,BaseMapper<R> baseMapper,Function<R,K> keyFunc,Function<R,V> valueFunc){
        List<R> list = selectList(clazz, baseMapper);
        return CollStreamUtil.groupKeyValue(list,keyFunc, valueFunc);
    }

    public static <T,R,E> Set<E> toSet(T clazz,BaseMapper<R> baseMapper,Function<R, E> setKey){
        List<R> list = selectList(clazz, baseMapper);
        return CollStreamUtil.toSet(list,setKey);
    }

    public static <T,R,E> List<E> toList(T clazz, BaseMapper<R> baseMapper, Function<R, E> listKey,boolean... distinct){
        List<E> eList = CollStreamUtil.toList(selectList(clazz, baseMapper), listKey);
        return distinct.length == 0 || !distinct[0] ? eList.stream().distinct().collect(Collectors.toList()) : eList;
    }


    public static <T> Class<T> getClass(BaseMapper<T> baseMapper){
        Class clazz = baseMapper.getClass();
        Type[] types = clazz.getGenericInterfaces();
        Type[] interfaces = ((Class) types[0]).getGenericInterfaces();
        Type typeArgument = ((ParameterizedType) interfaces[0]).getActualTypeArguments()[0];
        return (Class<T>)typeArgument;
    }
    //------------查询数据-----------
    public static <T,R> List<R> selectList(T clazz,BaseMapper<R> baseMapper){
       return baseMapper.selectList(getPredicate(clazz));
    }

    public static <T,R> R selectOne(T clazz,BaseMapper<R> baseMapper){
        return baseMapper.selectOne(getPredicate(clazz));
    }

    public static <T,R> Long selectCount(T clazz,BaseMapper<R> baseMapper){
        return Long.valueOf(baseMapper.selectCount(getPredicate(clazz)));
    }

    public static <T,R,P extends IPage<R>> P selectPage(P page,T clazz, BaseMapper<R> baseMapper){
        return baseMapper.selectPage(page,getPredicate(clazz));
    }




    public static <T,R> QueryWrapper<R> getPredicate(T clazz) {
        QueryWrapper<R> wrapper = new QueryWrapper<>();
        Field[] fields = ClassUtil.getDeclaredFields(clazz.getClass());
        if(!CollUtil.isEmpty(Arrays.asList(fields))){
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = ReflectUtil.getFieldValue(clazz, field.getName());
                if("".equals(fieldValue) || ObjectUtil.isNull(fieldValue)){
                    continue;
                }
                String fieldName = ReflectUtil.getFieldName(field);
                QueryMatching annotation = field.getAnnotation(QueryMatching.class);
                String matching = annotation.matching();
                switch (annotation.type()){
                    case eq:
                        wrapper.eq(fieldName,fieldValue);
                        break;
                    case ne:
                        wrapper.ne(fieldName,fieldValue);
                        break;
                    case like:
                        wrapper.like(fieldName,fieldValue);
                        break;
                    case likeLeft:
                        wrapper.likeLeft(fieldName,fieldValue);
                        break;
                    case likeRight:
                        wrapper.likeRight(fieldName,fieldValue);
                        break;
                    case gt:
                        wrapper.gt(fieldName,fieldValue);
                        break;
                    case ge:
                        wrapper.ge(fieldName,fieldValue);
                        break;
                    case lt:
                        wrapper.lt(fieldName,fieldValue);
                        break;
                    case le:
                        wrapper.le(fieldName,fieldValue);
                        break;
                    case in:
                        List list = (List) fieldValue;
                        wrapper.in(matching,list.toArray());
                        break;
                    case notIn:
                        List notInList = (List) fieldValue;
                        wrapper.notIn(fieldName,notInList);
                        break;
                    case is_null:
                        wrapper.isNull(fieldName);
                        break;
                    case is_not_null:
                        wrapper.isNotNull(fieldName);
                        break;
                    case apply:
                        wrapper.apply(matching,fieldValue);
                        break;
                    case between:
                        List between = (List) fieldValue;
                        wrapper.between(fieldName,between.get(0),between.get(1));
                        break;
                    case notBetween:
                        List notBetween = (List) fieldValue;
                        wrapper.between(fieldName,notBetween.get(0),notBetween.get(1));
                        break;
                }
            }
        }
        // order group
        OrderGroup annotation = clazz.getClass().getAnnotation(OrderGroup.class);
        if(annotation != null){
            String[] groups = annotation.groupBy();
            if(!ArrayUtil.isEmpty(groups)){
                for (String group : groups) {
                    wrapper.groupBy(!StrUtil.isBlank(group),group);
                }
            }
            OrderByIsAsc[] orderBys = annotation.orderBys();
            if(!ArrayUtil.isEmpty(orderBys)){
                for (OrderByIsAsc orderBy : orderBys) {
                    wrapper.orderBy(orderBy != null,orderBy.isAsc(),orderBy.column());
                }
            }
        }
        wrapper.last(Optional.ofNullable(annotation).map(e->e.last()).orElseGet(()->"limit 2000"));
        return wrapper;
    }

}
