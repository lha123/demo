package com.example.demo.po;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


@Data
public class UserInfo extends Model<UserInfo> {


    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Integer aaT;



}
