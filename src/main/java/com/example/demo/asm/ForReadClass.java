package com.example.demo.asm;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class ForReadClass {
    final int init = 110;
    private final Integer intField = 120;
    public final String stringField = "Public Final Strng Value";
    public static String commStr = "Common String value";
    String str = "Just a string value";
    final double d = 1.1;
    final Double D = 1.2;

    public ForReadClass() {
    }

    public void methodA() {
        String aa = show();
        String bbb = "liuhonger";
        System.out.println(intField);
    }



    public String show(){
        return "西打发第三";
    }

    public static void main(String[] args) throws FileNotFoundException {
        ClassPathResource d  = new ClassPathResource("mapper/CustomerMapper.xml");
        String path = d.getPath();
        System.out.println("ss"+path);
        String s = ResourceUtils.getURL("classpath:mapper/CustomerMapper.xml").getPath();
        System.out.println(s);
    }


}
