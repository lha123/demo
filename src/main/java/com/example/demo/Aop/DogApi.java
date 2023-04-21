package com.example.demo.Aop;

import com.example.demo.po.TestAa;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface DogApi {


    @PostMapping("/show")
    String show(@Valid @RequestBody TestAa a);
    @GetMapping("/show1")
     Integer show1(String a);
}
