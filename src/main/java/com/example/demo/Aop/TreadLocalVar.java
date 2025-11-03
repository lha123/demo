package com.example.demo.Aop;


import cn.hutool.core.collection.CollUtil;

import java.util.Map;

public class TreadLocalVar {
    private static final ThreadLocal<Map<String,Object>> stringThreadLocal  = new ThreadLocal();



    public static void set(Map<String,Object> map){
      stringThreadLocal.set(map);
    }

    public static Map<String,Object> get(){
      Map<String, Object> map = stringThreadLocal.get();
      if(!CollUtil.isEmpty(map)){
        map.put("timeMillis",(System.currentTimeMillis()-(long)map.get("timestamp")) +"ms");
      }
      return map;
    }

    public static void remove(){
      stringThreadLocal.remove();
    }

}
