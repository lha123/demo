package com.example.demo.design23.ZR;

public class BlacklistGatewayHandler extends GatewayHandler {

    @Override
    public void service() {
        System.out.println("黑名单拦截");
        if (this.next != null) {
            this.next.service();
        }
    }
}
