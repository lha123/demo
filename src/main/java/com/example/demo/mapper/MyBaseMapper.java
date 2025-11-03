package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 所有Mapper接口的基础接口
 * @param <T> 实体类型
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    // 可以在这里添加自定义的通用方法
} 