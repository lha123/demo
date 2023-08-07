package com.example.demo.Aop;


import com.example.demo.aa.UserVo;
import com.google.common.collect.Lists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/aspect")
public class AspectDemoRest {

    @Autowired
    private AspectDemoService demoService;

    @PostMapping("/show3")
    public  List<String> show3(@Valid @RequestBody UserVo userVo){
        System.out.println("adf");
        Integer show = demoService.show("111");
        return Lists.newArrayList("aaa","bbb");
    }
}
