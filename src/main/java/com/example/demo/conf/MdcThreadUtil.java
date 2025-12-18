package com.example.demo.conf;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Component
public class MdcPropagationUtil {

    /**
     * 包装Runnable传递MDC
     */
    public static Runnable wrapWithMdc(Runnable runnable) {
        Map<String, String> mdc = ObjectUtil.defaultIfNull(MDC.getCopyOfContextMap(),new HashMap<>());
        return () -> {
            MDC.setContextMap(mdc);
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * 包装Callable传递MDC
     */
    public static <T> Callable<T> wrapWithMdc(Callable<T> callable) {
        Map<String, String> mdc = ObjectUtil.defaultIfNull(MDC.getCopyOfContextMap(),new HashMap<>());
        return () -> {
            MDC.setContextMap(mdc);
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * 包装CompletableFuture
     */
    public static <T> CompletableFuture<T> wrapCompletableFuture(Supplier<T> supplier, Executor executor) {
        Map<String, String> mdc = ObjectUtil.defaultIfNull(MDC.getCopyOfContextMap(),new HashMap<>());
        return CompletableFuture.supplyAsync(() -> {
            MDC.setContextMap(mdc);
            try {
                return supplier.get();
            } finally {
                MDC.clear();
            }
        }, executor);
    }

    /**
     * 包装CompletableFuture
     */
    public static void wrapCompletableFuture(Runnable runnable, Executor executor) {
        Map<String, String> mdc = ObjectUtil.defaultIfNull(MDC.getCopyOfContextMap(),new HashMap<>());
        CompletableFuture.runAsync(() -> {
            MDC.setContextMap(mdc);
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        }, executor);
    }


    /**
     * 包装CompletableFuture
     */
    public static <T> CompletableFuture<T> wrapCompletableFuture(Supplier<T> supplier) {
        Map<String, String> mdc = ObjectUtil.defaultIfNull(MDC.getCopyOfContextMap(),new HashMap<>());
        return CompletableFuture.supplyAsync(() -> {
            MDC.setContextMap(mdc);
            try {
                return supplier.get();
            } finally {
                MDC.clear();
            }
        });
    }

    /**
     * 包装CompletableFuture
     */
    public static void wrapCompletableFuture(Runnable runnable) {
        Map<String, String> mdc = ObjectUtil.defaultIfNull(MDC.getCopyOfContextMap(),new HashMap<>());
        CompletableFuture.runAsync(() -> {
            MDC.setContextMap(mdc);
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        });
    }

}
