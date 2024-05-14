package com.example.demo.netty;

/**
 * 请求对象体
 */
public class Request {
    private String requestId;

    private Object parameter;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }
}
