package com.example.demo.po;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("userRoles")
public class UserRoles extends Model<UserRoles>{

    private Long id;

    private Long userId;

    private Long roleId;


}
