package com.example.demo.oauth2;


import lombok.SneakyThrows;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginRest implements SmartInitializingSingleton {

    @Autowired
    private AuthenticationConfiguration authenticationManager;
//http://127.0.0.1:8089/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.read
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @SneakyThrows
    @Override
    public void afterSingletonsInstantiated() {
        final AuthenticationManager managerAuthenticationManager = authenticationManager.getAuthenticationManager();
        System.out.println(managerAuthenticationManager);
    }
}
