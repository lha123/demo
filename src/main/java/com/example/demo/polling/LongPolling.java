package com.example.demo.polling;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 实现http长轮询
 * @author liuhonger
 */
@Slf4j
@Data
public class LongPolling implements Runnable{

    private Future<?> future;
    private String orderId;
    private AsyncContext asyncContext;
    private Long timeOut;

    public LongPolling(String orderId, AsyncContext asyncContext, Long timeOut) {
        this.orderId = orderId;
        this.asyncContext = asyncContext;
        this.timeOut = timeOut;
    }

    @Override
    public void run() {
        future = LongPollingQueueUtil.scheduled.schedule(()->{
            if(LongPollingQueueUtil.pollingMap.containsKey(orderId)){
                sendResponse(null);
            }
        },timeOut, TimeUnit.SECONDS);
        LongPollingQueueUtil.pollingMap.put(orderId,this);
    }


    public void sendResponse(String orderId) {
        if(StrUtil.isBlank(orderId)){
            asyncContext.complete();
            return;
        }
        try {
            String orderInfo = "succuss";
            HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
            response.setHeader("Pragma","no-cache");
            response.setDateHeader("Expires",0);
            response.setHeader("Cache-Control","no-cache,no-store");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(orderInfo);
            asyncContext.complete();
        } catch (IOException e) {
           log.error(e.getMessage(),e);
            asyncContext.complete();
        }

    }


}
