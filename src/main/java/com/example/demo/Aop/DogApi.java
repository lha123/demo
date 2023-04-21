package com.example.demo.Aop;

import com.example.demo.po.TestAa;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface DogApi {


    @PostMapping("/show")
    String show(@Valid @RequestBody TestAa a);
    @GetMapping("/show1")
     Integer show1(String a);
}
