package com.example.demo.po;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@TableName(autoResultMap = true)
public class UserInfo extends Model<UserInfo> {


    private Long id;

    @NotBlank
    private String name;

    private Integer age;

    private String email;

    private Integer aaT;



}
