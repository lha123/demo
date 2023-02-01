package com.example.demo.po;


import com.example.demo.annotation.Switch;
import lombok.Data;

@Data
public class UserInfoRolesVo {

    private Long id;

    private String name;

    private Integer age;

    private String email;


    private Integer aaT;

    @Switch(source = "aaT",value = "1:a|2:b")
    private String aaTs;

    private Long roleId;

    private Long userId;

}
