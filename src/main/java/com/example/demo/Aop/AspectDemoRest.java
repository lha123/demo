package com.example.demo.Aop;


import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/aspect")
public class AspectDemoRest {

    @Autowired
    private AspectDemoService demoService;

    @GetMapping("/show3")
    public  List<String> show3(String a){
        Integer show = demoService.show("111");
        return Lists.newArrayList("aaa","bbb");
    }
}
