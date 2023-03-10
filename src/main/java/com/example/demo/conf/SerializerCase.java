package com.example.demo.conf;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@JacksonStdImpl
public class SerializerCase extends StdConverter<EnumsObject,String> {

   private static Map<String,Map<Integer,String>> jsonMap = new ConcurrentHashMap<>();




    @Override
    public String convert(EnumsObject object) {
        return jsonMap(object);
    }

    public String jsonMap(EnumsObject object){
      return null;

    }
}
