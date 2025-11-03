package com.example.demo.Aop;


import com.alipay.api.domain.UserVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/aspect")
@Slf4j
public class AspectDemoRest {

    @Autowired
    private AspectDemoService demoService;

    @PostMapping("/show3")
    public  List<String> show3(@Valid @RequestBody UserVo userVo){
        System.out.println("adf");
        Integer show = demoService.show("111");
        return Lists.newArrayList("aaa","bbb");

    }

    @PostMapping("/show4")
    // 定义一个POST请求，请求路径为/show4
    public List<String> show3() {
        // 定义一个返回类型为List<String>的方法
        return Lists.newArrayList("aaa", "bbb");
        // 返回一个包含两个字符串的List
    }
}
