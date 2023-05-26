package com.example.demo.po;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles extends Model<UserRoles>{

    private Long id;

    private Long userId;

    private Long roleId;


}
