package com.example.demo.design23;

public class UserFactory {


    public UserService newUserServiceInstance(Class<? extends UserService> tClass){
        try {
            return tClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
