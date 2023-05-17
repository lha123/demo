package com.example.demo.aa;

import com.example.demo.Aop.DogService;
import com.example.demo.annotation.BizImplements;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ${author}
 * @since ${date}
 */
@RestController
@BizImplements(DogService.class)
@Api(value = "公告模块3", description = "公告模块3", tags = {"公告模块3"})
public interface TestService  {
    @PostMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息")
    UserVo getUserInfo(@Valid @RequestBody UserFrom userFrom);
    @PostMapping("/getCustomerInfo")
    @ApiOperation(value = "获取客户信息",notes = "获取客户信息")
    CustomerVo getCustomerInfo(@Valid @RequestBody CustomerFrom customerFrom);
}
