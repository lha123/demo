package com.example.demo.polling;

import cn.hutool.core.thread.ThreadUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class LongPollingQueueUtil {


    public static final Map<String,LongPolling> pollingMap = new HashMap<>();


    public static final ScheduledThreadPoolExecutor scheduled = ThreadUtil.createScheduledExecutor(5);


    public static final ExecutorService threadPoll = ThreadUtil.newExecutor(5);



}
