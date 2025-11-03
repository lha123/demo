package com.example.demo.annotation;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface YkcMybatisRepo<T> extends IService<T> {



    default T getOne(T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return getOne(queryWrapper, true);
    }

    default List<T> list(T t) {
        Wrapper<T> queryWrapper = new QueryWrapper<>(t);
        return list(queryWrapper);
    }

    default <K> Map<K,T> getYkcEntityByIdsToMap(Function<T, K> k, Serializable... id){
        Collection<Serializable> ids = CollUtil.newHashSet(id);
        return CollUtil.isEmpty(ids)?MapUtil.empty():CollStreamUtil.toMap(this.listByIds(ids), k, Function.identity());
    }

    default <K,V> Map<K,V> getYkcEntityByIdsToMap(Function<T,K> k,Function<T,V> v,Serializable... id){
        Collection<Serializable> ids = CollUtil.newHashSet(id);
        return CollUtil.isEmpty(ids)?MapUtil.empty():CollStreamUtil.toMap(this.listByIds(ids), k, v);
    }

    default <K> Map<K,T> getYkcEntityByIdsToMap(Function<T, K> k, Collection<? extends Serializable> idList){
        return CollUtil.isEmpty(idList)?MapUtil.empty():CollStreamUtil.toMap(this.listByIds(idList), k, Function.identity());
    }

    default <K,V> Map<K,V> getYkcEntityByIdsToMap(Function<T,K> k,Function<T,V> v,Collection<? extends Serializable> idList){
        return CollUtil.isEmpty(idList)?MapUtil.empty():CollStreamUtil.toMap(this.listByIds(idList), k, v);
    }


     Wrapper<T> getWrapper(Object clazz);

     Wrapper<T> getListWrapper(Map<String,Object> linkMap);

}