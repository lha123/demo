package com.example.demo.design23.Genericity;

public class OrderDetailNativeService<T,R> implements OrderQueryDetail<T,R>{


    @Override
    public R getOrderDetail(T orderId) {

        return null;
    }
}
