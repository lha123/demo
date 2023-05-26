package com.example.demo.func;

import java.io.Serializable;

@FunctionalInterface
public interface QueryDbFun<T> {

    T query(Serializable id);
}
