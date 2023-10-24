package com.example.demo;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Slf4j
public class LogTest {


    public static void main(String[] args) {
        ExecutorService executorService = ThreadUtil.newSingleExecutor();
    }
}

