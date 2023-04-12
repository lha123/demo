package com.example.demo.Aop;


import com.example.demo.annotation.BizImplements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@BizImplements(Cat.class)
public interface CatInterfce {


    @GetMapping("/user")
   default String show(){
       System.out.println("adf");
       return "sdf";
   }
}
