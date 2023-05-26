package com.example.demo.polling;

public class DataCallBackTask implements Runnable{


    private String orderId;

    public DataCallBackTask(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        if(LongPollingQueueUtil.pollingMap.containsKey(orderId)){
            LongPolling longPolling = LongPollingQueueUtil.pollingMap.get(orderId);
            longPolling.sendResponse(orderId);
            LongPollingQueueUtil.pollingMap.remove(orderId);
        }
    }
}
