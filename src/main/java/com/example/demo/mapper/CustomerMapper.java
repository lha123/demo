package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.po.UserInfo;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author magang
 * @since 2019-11-18
 */
@Mapper
public interface CustomerMapper extends MPJBaseMapper<UserInfo> {

    @Select("select * from user_info where id = #{id}")
    UserInfo selectByInfo(@Param("id") Integer id);

    UserInfo selectByUser(@Param("id")Integer id);

    void updateUser(@Param("name")String name,@Param("id")Integer id);


}
