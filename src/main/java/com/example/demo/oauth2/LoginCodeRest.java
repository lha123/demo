package com.example.demo.oauth2;

import cn.hutool.http.Header;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginCodeRest {


    @RequestMapping("/login/oauth2/code/messaging-client-oidc")
    public void code(@RequestParam("code") String code){
        System.out.println("code===>"+code);
        Map<String,Object> map =new HashMap<>();
        map.put("code",code);
        map.put("grant_type","authorization_code");
        String result2 = HttpUtil.createPost("localhost:8089/oauth2/token")
                .header(Header.AUTHORIZATION, "Basic bWVzc2FnaW5nLWNsaWVudDpzZWNyZXQ=")//头信息，多个头信息多次调用此方法即可
                .form(map)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();

        System.out.println(JSONUtil.formatJsonStr(result2));
    }
}
