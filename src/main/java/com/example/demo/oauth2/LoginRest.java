package com.example.demo.oauth2;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginRest {
//http://127.0.0.1:8089/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.read&redirect_uri=http://127.0.0.1:8089/login/oauth2/code/messaging-client-oidc
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
