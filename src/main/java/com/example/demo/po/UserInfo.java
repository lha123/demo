package com.example.demo.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;


@Data
@TableName(value = "t_user")
@FieldNameConstants
public class UserInfo {


    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("`name`")
    private String name;

    private Integer age;

    private String email;

    private Integer roleId;




}
