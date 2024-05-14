package com.example.demo.rest;


import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.json.JSONUtil;
import com.example.demo.component.TestSingleton;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.TestAa;
import com.example.demo.po.UserInfo;
import com.example.demo.servcie.CustomerServcie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@Api(value = "公告模块1", description = "公告模块1", tags = {"公告模块1"})
public class CustomerRest {

    @Autowired
    private CustomerServcie customerImplTest;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Lazy
    private TestSingleton testSingleton;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private QrConfig qrconig;

    private ApplicationContext context;
    /**
     * 获取当前请求
     *
     * @return request
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }


    @SneakyThrows
    @ApiOperation(value = "添加修改公告1",notes = "添加修改公告1")
    @GetMapping("/test")
    public TestAa test(HttpServletRequest request1) throws InterruptedException {
        HttpServletRequest request = getRequest();
        System.out.println(getRequest());
        TestAa aa = new TestAa();
        aa.setCode(123);
        aa.setCode1(321);
        return aa;
    }


    @GetMapping(value = "/show321")
    public String show13(){

        log.info("erwerwe========>");
        System.out.println("dsff");

       UserInfo userInfo = customerMapper.selectByUser(81);
        System.out.println(JSONUtil.toJsonPrettyStr(userInfo));
        return "show1===dfdf=====eee===========>";
    }

    @RequestMapping("123")
    public void generateV3(String content,HttpServletRequest request, HttpServletResponse response) throws IOException {
        QrCodeUtil.generate(System.currentTimeMillis()+"",qrconig,"png",response.getOutputStream());
    }

    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        String json = "[{\"age\":10,\"name\":\"曹操\"}]";
        List<UserInfo> userInfo = objectMapper.readValue(json, ArrayList.class);

        System.out.println(userInfo);
    }

}
