package com.example.demo.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

public class javassistTest {

    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault();
        cp.importPackage("com.baomidou.mybatisplus.annotation.TableField");
        cp.importPackage("com.comma.teeth.util.JacksonArrayTypeHandler");
        cp.importPackage("org.apache.ibatis.reflection.TypeParameterResolver");
        cp.importPackage("java.lang.reflect.ParameterizedType");
        cp.importPackage("java.lang.reflect.Field");
        cp.importPackage("java.lang.reflect.Type");
        CtClass ctClass = cp.get("org.apache.ibatis.reflection.Reflector");
        CtMethod foo = ctClass.getDeclaredMethod("addGetMethod");
        foo.setModifiers(Modifier.PRIVATE);
        foo.insertAfter("Field declaredField = type.getDeclaredField(name);\n" +
                "        if(declaredField.isAnnotationPresent(TableField.class)){\n" +
                "            TableField declaredAnnotation = declaredField.getDeclaredAnnotation(TableField.class);\n" +
                "            Class aClass = declaredAnnotation.typeHandler();\n" +
                "            if(aClass != null && aClass.isAssignableFrom(JacksonArrayTypeHandler.class)){\n" +
                "                Type returnType = TypeParameterResolver.resolveReturnType(method, type);\n" +
                "                getTypes.put(name, (Class) ((ParameterizedType) returnType).getActualTypeArguments()[0]);\n" +
                "            }\n" +
                "        }");
        ctClass.toClass();

    }
}
