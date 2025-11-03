package com.example.demo.Aop;

import com.example.demo.po.TestAa;
import com.example.demo.po.UserInfoRolesVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@Slf4j
@RequestMapping(value = "/car")
public class CarRest {

    @Autowired
    private DogServiceApi dogServiceApi;


    @GetMapping(value = "/show")
    public String show1(HttpServletRequest request) {
        return "111";
    }

    @PostMapping(value = "/show2")
    public List<UserInfoRolesVo> show2(@RequestBody TestAa testAa) {

        dogServiceApi.show(testAa);

        return null;
    }



}
