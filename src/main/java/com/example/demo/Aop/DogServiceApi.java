package com.example.demo.Aop;

import com.example.demo.po.DogShow1;
import com.example.demo.po.TestAa;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public interface DogServiceApi {


    TestAa show(@Valid @RequestBody TestAa a);

    DogShow1 show1();

}
