package com.example.demo.Aop;

public interface CatInterfce {

   default String show(){
       System.out.println("adf");
       return "sdf";
   }
}
