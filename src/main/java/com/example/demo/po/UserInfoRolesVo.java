package com.example.demo.po;


import com.example.demo.conf.EnumsObject;
import com.example.demo.conf.SerializerCase;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoRolesVo implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Integer aaT;

    @JsonSerialize(converter = SerializerCase.class)
    private EnumsObject aaTs ;

    private Long roleId;

    private Long userId;


}
