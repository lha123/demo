package com.example.demo.mapper;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.po.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


@Mapper
public interface ObjectMapper extends BaseMapper<Object> {

    @Update({"create table IF NOT EXISTS ${tableName}(${ddl}) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"})
    int createTable(@Param("tableName") String table, @Param("ddl") String ddl);

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
    @Insert("insert into ${table} ${data} ")
    int insert(@Param("table") String table, @Param("data") String data);

    /**
     * 修改
     *
     * @return 结果
     */
    @Update("update ${table} set ${ew.getSqlSet} where ${ew.getSqlSegment}")
    int updateCondition(@Param("table") String table, @Param("ew") UpdateWrapper data);

    /**
     * 删除
     *
     * @param id 测试主键
     * @return 结果
     */
    @Delete("delete from ${table} where id = #{id}")
    int deleteById(@Param("table") String table, @Param("id") Integer id);

}
