package com.example.demo.Aop;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(value = "/car")
public class CarRest implements CarApi{
    @Override
    public String show1(List<String> a) {
        System.out.println("aa");
        return "111";
    }
}
