package com.example.demo.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TestAsm {

    /**
     * @param args
     * @author lihzh
     * @date 2012-4-21 下午10:17:22
     */
    public static void main(String[] args) {
        try {
            ForReadClass forReadClass = new ForReadClass();
            forReadClass.methodA();
            ClassReader reader = new ClassReader(
                    "com.example.demo.asm.ForReadClass");
            ClassNode cn = new ClassNode();
            reader.accept(cn, 0);
            List<MethodNode> methodList = cn.methods;
            for (MethodNode md : methodList) {
                if(!md.name.equals("methodA")){
                   continue;
                }
                System.out.println(md.name);
                List<LocalVariableNode> lvNodeList = md.localVariables;
                for (LocalVariableNode lvn : lvNodeList) {
                    System.out.println("Local name: " + lvn.name);
                }
                Iterator<AbstractInsnNode> instraIter = md.instructions.iterator();
                while (instraIter.hasNext()) {
                    AbstractInsnNode abi = instraIter.next();
                    if (abi instanceof LdcInsnNode) {
                        LdcInsnNode ldcI = (LdcInsnNode) abi;
                        System.out.println("LDC node value: " + ldcI.cst);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
