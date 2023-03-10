package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.Aaa;
import com.example.demo.po.Customer;
import com.example.demo.po.UserInfo;
import com.example.demo.po.UserRoles;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cursor.Cursor;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author magang
 * @since 2019-11-18
 */
@Mapper
public interface CustomerMapper extends BaseMapper<UserInfo> {




}
