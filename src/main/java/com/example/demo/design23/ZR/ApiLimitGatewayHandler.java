package com.example.demo.design23.ZR;

public class ApiLimitGatewayHandler extends GatewayHandler {

    @Override
    public void service() {
        System.out.println("api接口限流");
        if (this.next != null) {
            this.next.service();
        }
    }
}
