package com.example.demo.Aop;


import com.example.demo.annotation.BizImplements;
import org.springframework.web.bind.annotation.RestController;

@RestController
@BizImplements(Dog.class)
public interface DogRest extends DogApi{


}
