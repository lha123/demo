package com.example.demo.func;

@FunctionalInterface
public interface ThrowException {

    void throwMessage(String errorMessage);

    static ThrowException isTrue(boolean b){
        return (errorMessage)->{
            if(b){
                throw new RuntimeException(errorMessage);
            }
        };

    }
}
