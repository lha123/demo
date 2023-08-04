package com.example.demo.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloTest {

    @SneakyThrows
    public static void main(String[] args) {
        ClassPool cp = ClassPool.getDefault();
        CtClass ctClass = cp.get("com.example.demo.javassist.HisHello");
        CtMethod foo = ctClass.getDeclaredMethod("show1");
        foo.setModifiers(Modifier.PUBLIC);
        foo.insertAfter("System.out.println(\"df\");");
        ctClass.toClass();
        new HisHello().show1();
    }

}
