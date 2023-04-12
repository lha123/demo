package com.example.demo.po;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
public class UserRoles extends Model<UserRoles>{

    private Long id;

    private Long userId;

    private Long roleId;


}
