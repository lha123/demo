package com.example.demo.design23.ZR;

public abstract class GatewayHandler {

    /**
     * 下一关用当前抽象类来接收
     */
    protected GatewayHandler next;

    public void setNext(GatewayHandler next) {
        this.next = next;
    }

    public abstract void service();
}
