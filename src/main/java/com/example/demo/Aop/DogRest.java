package com.example.demo.Aop;


import com.example.demo.annotation.BizImplements;
import com.example.demo.po.DogShow1;
import com.example.demo.po.TestAa;
import com.example.demo.po.UserInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@BizImplements(DogService.class)
@Api(value = "公告模块2", description = "公告模块2", tags = {"公告模块2"})
public interface DogRest extends DogApi{

    @GetMapping("/show2")
    default Integer show2(String a){
        System.out.println("default");
        return 1;
    }

    @GetMapping("/show3")
    default List<String> show3(String a){
        System.out.println("default");
        return Lists.newArrayList("aaa","bbb");
    }

    @GetMapping("/show4/{userId}")
    default List<String> show4(@PathVariable Integer userId){
        System.out.println(userId);
        return Lists.newArrayList("aaa","bbb");
    }

    @PostMapping("/test1")
   default UserInfo test(@Valid @RequestBody TestAa a){
        return new UserInfo();
    }

    @GetMapping("/show55")
    default DogShow1 show55(Integer a){
        return show1(Lists.newArrayList("22"));

    }


}
