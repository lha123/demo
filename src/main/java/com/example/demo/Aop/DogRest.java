package com.example.demo.Aop;


import com.example.demo.annotation.BizImplements;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

@RestController
@BizImplements(Dog.class)
@Api(value = "公告模块2", description = "公告模块2", tags = {"公告模块2"})
public interface DogRest extends DogApi{


}
