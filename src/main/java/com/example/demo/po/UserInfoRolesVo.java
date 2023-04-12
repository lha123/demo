package com.example.demo.po;


import com.example.demo.conf.Date2LongSerializer;
import com.example.demo.conf.EnumsObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class UserInfoRolesVo  {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Integer aaT;

    @JsonSerialize(using = Date2LongSerializer.class)
    private EnumsObject aaTs ;

    private Long roleId;

    private Long userId;


}
