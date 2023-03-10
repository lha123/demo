package com.example.demo.conf;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class StrJsonSerialize extends StdConverter<Integer,String> {

   private static Map<String,Map<Integer,String>> jsonMap = new ConcurrentHashMap<>();

    public StrJsonSerialize(){
        System.out.println("sdf");
    }

    @Override
    public String convert(Integer integer) {
        return "sdf";
    }
}
