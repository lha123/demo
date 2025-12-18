package com.example.demo.conf;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class SleuthInterceptor implements AsyncHandlerInterceptor {

    private static final TransmittableThreadLocal<Map<String, Object>> CONTEXT = new TransmittableThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String string = MDC.get("traceId");
        System.out.println("aa");
        MdcThreadUtil.wrapCompletableFuture(()->{
           log.info("123123");
        });

        return true;
    }
}
