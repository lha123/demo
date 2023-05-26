package com.example.demo.po;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.demo.annotation.Switch;
import lombok.Data;

import java.util.List;
import java.util.function.Supplier;


@Data
public class UserInfo extends Model<UserInfo> {


    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Integer aaT;



}
