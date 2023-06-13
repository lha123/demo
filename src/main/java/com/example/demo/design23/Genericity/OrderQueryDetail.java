package com.example.demo.design23.Genericity;

public interface OrderQueryDetail<T,R> {

    R getOrderDetail(T orderId);
}
