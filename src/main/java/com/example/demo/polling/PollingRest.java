package com.example.demo.polling;


import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PollingRest {



    @SneakyThrows
    @RequestMapping(value = "/listener")
    public void listener(HttpServletRequest request, HttpServletResponse response, String orderId, String orderInfo){

        String orderInfoCache = "a";

        if(!orderInfo.equals(orderInfoCache)){ //如果不相同 就直接返回  如果相同hold住连接
            response.setHeader("Pragma","no-cache");
            response.setDateHeader("Expires",0);
            response.setHeader("Cache-Control","no-cache,no-store");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(orderInfoCache);
            return;
        }
        AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(0L);
        Long timeOut = 15L;
        LongPollingQueueUtil.threadPoll.execute(new LongPolling(orderId,asyncContext,timeOut));
    }

    @RequestMapping(value = "/callBack")
    public void callBack(String orderId,String orderInfo){
        LongPollingQueueUtil.threadPoll.execute(new DataCallBackTask(orderId));
    }
}
