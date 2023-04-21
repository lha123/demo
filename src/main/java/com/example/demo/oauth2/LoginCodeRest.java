package com.example.demo.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginCodeRest {


    @RequestMapping("/message")
    public void code(@RequestParam("code") String code){
        System.out.println("aa");

    }
}
