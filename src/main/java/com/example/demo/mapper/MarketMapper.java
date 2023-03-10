package com.example.demo.mapper;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface MarketMapper extends MPJBaseMapper<Object> {

    /**
     * 查询
     *
     * @param id 主键
     * @return 测试
     */
    @Select("select * from ${table} where id = #{id}")
    JSONObject selectById(@Param("table") String table, @Param("id") Integer id);

    /**
     * 查询列表
     *
     * @return 测试集合
     */
    @Select("select * from ${table} ${ew.customSqlSegment}")
    List<JSONObject> selectList(@Param("table") String table, @Param("ew") QueryWrapper data);


    @Select("select count(*) from ${table} ${ew.customSqlSegment}")
    int count(@Param("table") String table, @Param("ew") QueryWrapper data);

    /**
     * 新增
     *
     * @return 结果
     */
    @Insert("insert into ${table} ${ew.getSql}")
    int insert(@Param("table") String table, @Param("ew") Object data);

    /**
     * 修改
     *
     * @return 结果
     */
    @Update("update ${table} set ${ew.getSqlSet} where ${ew.getSqlSegment}")
    int updateCondition(@Param("table") String table, @Param("ew") UpdateWrapper data);


}
