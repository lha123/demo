package com.example.demo.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.*;
import cn.hutool.extra.spring.SpringUtil;
import com.example.demo.annotation.*;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuhonger 多表连接 待完善
 */
public class QueryWrapJoinUtil  {

    @SneakyThrows
    private static <T> void converSwitchList(List<T> list,Class<?> switchClass){
        if(!CollUtil.isEmpty(list)){
            Set<Field> fields = Arrays.stream(switchClass.getDeclaredFields())
                    .filter(e -> e.isAnnotationPresent(Switch.class))
                    .collect(Collectors.toSet());
            if(!CollUtil.isEmpty(fields)){
                for (T t : list) {
                    Class<?> aClass = t.getClass();
                    List<Field> fieldList = Arrays.stream(aClass.getDeclaredFields())
                            .filter(e -> e.isAnnotationPresent(Switch.class)).collect(Collectors.toList());
                    for (Field field : fieldList) {
                        Switch annotation = field.getAnnotation(Switch.class);
                        Field sField = aClass.getDeclaredField(annotation.source());
                        sField.setAccessible(true);
                        Map<String,String> convertMap = Splitter.on("|").withKeyValueSeparator(":").split(annotation.value());
                        field.setAccessible(true);
                        field.set(t,convertMap.get(String.valueOf(sField.get(t))));
                    }
                }
            }
        }
    }


    //---------数据转换-------------
//    public static <T,R,E,K,V> Map<K,V> toMapWithObj(T paramClass,Class<R> returnClass, MPJBaseMapper<E> baseMapper, Function<R,K> keyFunc, Function<R,V> valueFunc){
//        List<R> list = selectJoinList(paramClass,returnClass, baseMapper);
//        return CollStreamUtil.toMap(list,keyFunc, valueFunc);
//    }
//
//    public static<T,R,E,K,V> Map<K, List<V>> toMapWithGroup(T paramClass,Class<R> returnClass,MPJBaseMapper<E> baseMapper,Function<R,K> keyFunc,Function<R,V> valueFunc){
//        List<R> list = selectJoinList(paramClass,returnClass, baseMapper);
//        return CollStreamUtil.groupKeyValue(list,keyFunc, valueFunc);
//    }
//
//    public static <T,R,E,M> Set<M> toSet(T paramClass,Class<R> returnClass, MPJBaseMapper<E> baseMapper, Function<R, M> setKey){
//        List<R> list = selectJoinList(paramClass,returnClass, baseMapper);
//        return CollStreamUtil.toSet(list,setKey);
//    }
//
//    public static <T,R,E,M> List<M> toList(T paramClass,Class<R> returnClass, MPJBaseMapper<E> baseMapper, Function<R, M> listKey,boolean... distinct){
//        List<M> eList = CollStreamUtil.toList(selectJoinList(paramClass,returnClass, baseMapper), listKey);
//        return distinct.length == 0 || !distinct[0] ? eList.stream().distinct().collect(Collectors.toList()) : eList;
//    }


    //------------查询数据-----------
    public static <R> List<R> selectJoinList(Object paramClass){
        ChooseMapper annotation = AnnotationUtil.getAnnotation(paramClass.getClass(), ChooseMapper.class);
        Class<R> selectAsClass = (Class<R>) annotation.selectAsClass();
        MPJBaseMapper<?> baseMapper = (MPJBaseMapper)SpringUtil.getBean(annotation.mapperClass());
        List<R> selectJoinList = baseMapper.selectJoinList(selectAsClass, getJoinPredicate(paramClass, ClassUtil.getTypeArgument(annotation.mapperClass()), selectAsClass));
        return selectJoinList;
    }

//    public static <T,R,E> R selectJoinOne(T paramClass,Class<R> returnClass, MPJBaseMapper<E> baseMapper){
//        R joinOne = baseMapper.selectJoinOne(returnClass, getJoinPredicate(paramClass, baseMapper, returnClass));
//        converSwitchList(Lists.newArrayList(joinOne),returnClass);
//        return joinOne;
//    }
//
//    public static <T,R,E,P extends IPage<R>> IPage<R> selectJoinPage(P page, T paramClass,Class<R> returnClass, MPJBaseMapper<E> baseMapper){
//        IPage<R> riPage = baseMapper.selectJoinPage(page, returnClass, getJoinPredicate(paramClass, baseMapper, returnClass));
//        converSwitchList(riPage.getRecords(),returnClass);
//        return riPage;
//    }



    private static <T,R> MPJLambdaWrapper<R> getJoinPredicate(T paramClass,Class<?> sourceClass,Class<?> returnClass) {
        MPJLambdaWrapper<R> wrapper = MPJWrappers.lambdaJoin();
        List<Field> fieldList = getFields(paramClass.getClass());
        if(!CollUtil.isEmpty(fieldList)){
            //selectAll
            wrapper.selectAsClass(sourceClass,returnClass);
            for (Field field : fieldList) {
                field.setAccessible(true);
                Object fieldValue = ReflectUtil.getFieldValue(paramClass, field.getName());
                if("".equals(fieldValue) || ObjectUtil.isNull(fieldValue)){
                    continue;
                }
                QueryMatching annotation = field.getAnnotation(QueryMatching.class);
                //join as select
                if(fieldValue instanceof Class<?>){
                    JoinInfo joinInfos = annotation.join();
                    if(joinInfos != null){
                        String[] select = joinInfos.select();
                        if(select != null && select.length > 0){
                            wrapper.select(select);
                        }
                        wrapper.join(joinInfos.joinType(),true,StrUtil.join(" ",joinInfos.on()));
                    }
                    continue;
                }
                // where
                String fieldName = StrUtil.format("{}{}", StrUtil.lowerFirst(annotation.alias().getSimpleName()).concat("."),StrUtil.toUnderlineCase(ReflectUtil.getFieldName(field)));
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
        // order group limit
        OrderGroup annotation = paramClass.getClass().getAnnotation(OrderGroup.class);
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

    public static  <T> List<Field> getFields(Class<T>... paramClass){
        List<Field> fieldList = Lists.newArrayList();
        if(paramClass != null && paramClass.length > 0){
            List<Field> fields = Arrays.stream(paramClass)
                    .map(e->e.getDeclaredFields())
                    .flatMap(Arrays::stream).collect(Collectors.toList());
            fieldList.addAll(fields);
        }
        return fieldList;
    }

}
