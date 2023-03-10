package com.example.demo.func;

@FunctionalInterface
public interface BranchHandle {

    void trueOrFalseHandle(Runnable trueHandler,Runnable falseHandler);


     static BranchHandle isTrueOrFalse(boolean condition){
         return (trueHandler,falseHandler)->{
             if(condition){
                 trueHandler.run();
             }else{
                 falseHandler.run();
             }
         };
    }
}
