package com.example.demo.Aop;

import com.example.demo.po.DogShow1;
import com.example.demo.po.TestAa;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public interface DogApi {


    @PostMapping("/show")
    @ApiOperation(value = "添加修改公告",notes = "添加修改公告",httpMethod = "")
    TestAa show(@Valid @RequestBody TestAa a);
    @GetMapping("/show1")
    DogShow1 show1(@NotNull @Size(min = 1) List<String> a);

}
