package com.example.demo.Aop;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotEmpty;
import java.util.List;


public interface CarApi {

    @PostMapping("/showCar")
    String show1(List<String> a);
}
