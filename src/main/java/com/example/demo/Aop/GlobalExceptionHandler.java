package com.example.demo.Aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.conf.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static com.alibaba.nacos.common.http.param.MediaType.APPLICATION_JSON;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 默认的请求内容类型,表单提交
     **/
    private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    /**
     * JSON请求内容类型
     **/
    private static final String APPLICATION_JSON = "application/json";



    /**
     * 非法参数验证异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> list = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            list.add(fieldError.getDefaultMessage());
        }
        Collections.sort(list);
        log.error("fieldErrors" + JSON.toJSONString(list));
        return Response.failed(500, JSON.toJSONString(list));
    }


    /**
     * 非法参数验证异常
     *
     * @param ex ConstraintViolationException
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response handleMethodArgumentNotValidExceptionHandler(ConstraintViolationException ex) {
        List<String> list = new ArrayList<>();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            list.add(violation.getMessage());
        }
        return Response.failed(500, JSON.toJSONString(list));
    }




    /**
     * 默认的异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Response exceptionHandler(HandlerMethod handlerMethod,Exception exception) {
        log.error("requestInfo:{}", JSONUtil.toJsonPrettyStr(TreadLocalVar.get()));
        log.error("exception:", exception);
        show();
        TreadLocalVar.remove();
        return Response.failed(500,exception.getMessage());
    }



    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public String asyncRequestTimeoutHandler(AsyncRequestTimeoutException e) {
        System.out.println("异步请求超时");
        return "304";
    }

    private void show(){
        try {
            // 获取当前的HttpServletRequest对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {

                HttpServletRequest request = attributes.getRequest();


                Map<String, Object> map = new LinkedHashMap<>();



                // 获取请求类名和方法名称
                Signature signature = null;

                // 获取真实的方法对象
                MethodSignature methodSignature = (MethodSignature) signature;
                Method method = methodSignature.getMethod();

                // 请求全路径
                String url = request.getRequestURI();
                map.put("path", url);
                map.put("http",request.getRequestURL().toString());
                // IP地址
                String ip = ServletUtil.getClientIP(request);
                map.put("ip", ip);

                // 获取请求方式
                String requestMethod = request.getMethod();
                map.put("requestMethod", requestMethod);

                // 获取请求内容类型
                String contentType = request.getContentType();
                map.put("contentType", contentType);

                // 判断控制器方法参数中是否有RequestBody注解
                Annotation[][] annotations = method.getParameterAnnotations();
                boolean isRequestBody = isRequestBody(annotations);
                map.put("isRequestBody", isRequestBody);
                // 设置请求参数
                Object requestParamJson = getRequestParamJsonString(request, requestMethod, contentType, isRequestBody);
                map.put("param", requestParamJson);
                map.put("time", DateUtil.now());
                map.put("timestamp", System.currentTimeMillis());

                // 获取请求头token
                map.put("token", request.getHeader("token"));
                map.put("version", request.getHeader("version"));

                String requestInfo = JSON.toJSONString(map);
                log.info("requestInfo:{}", requestInfo);
                TreadLocalVar.set(map);

            } else {
                log.info("websocket 请求");
            }


        } catch (Exception e) {
            log.error("日志记录异常，异常原因:", e);
        }
    }


    /**
     * 判断控制器方法参数中是否有RequestBody注解
     *
     * @param annotations
     * @return
     */
    private boolean isRequestBody(Annotation[][] annotations) {
        boolean isRequestBody = false;
        for (Annotation[] annotationArray : annotations) {
            for (Annotation annotation : annotationArray) {
                if (annotation instanceof RequestBody) {
                    isRequestBody = true;
                }
            }
        }
        return isRequestBody;
    }


    /**
     * 获取请求参数JSON字符串
     *
     * @param joinPoint
     * @param request
     * @param requestMethod
     * @param contentType
     * @param isRequestBody
     */
    private Object getRequestParamJsonString(HttpServletRequest request, String requestMethod, String contentType, boolean isRequestBody) {
        /**
         * 判断请求内容类型
         * 通常有3中请求内容类型
         * 1.发送get请求时,contentType为null
         * 2.发送post请求时,contentType为application/x-www-form-urlencoded
         * 3.发送post json请求,contentType为application/json
         * 4.发送post json请求并有RequestBody注解,contentType为application/json
         */
        Object paramObject = null;
        int requestType = 0;
        if ("GET".equals(requestMethod)) {
            requestType = 1;
        } else if ("POST".equals(requestMethod)) {
            if (contentType == null) {
                requestType = 5;
            } else if (contentType.startsWith(APPLICATION_X_WWW_FORM_URLENCODED)) {
                requestType = 2;
            } else if (contentType.startsWith(APPLICATION_JSON)) {
                if (isRequestBody) {
                    requestType = 4;
                } else {
                    requestType = 3;
                }
            }
        }

        // 1,2,3中类型时,获取getParameterMap中所有的值,处理后序列化成JSON字符串
        if (requestType == 1 || requestType == 2 || requestType == 3 || requestType == 5) {
            Map<String, String[]> paramsMap = request.getParameterMap();
            paramObject = getJsonForParamMap(paramsMap);
        }

        return paramObject;
    }

    /**
     * 获取参数Map的JSON字符串
     *
     * @param paramsMap
     * @return
     */
    public JSONObject getJsonForParamMap(Map<String, String[]> paramsMap) {
        int paramSize = paramsMap.size();
        if (paramsMap == null || paramSize == 0) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String[]> kv : paramsMap.entrySet()) {
            String key = kv.getKey();
            String[] values = kv.getValue();
            if (values == null) { // 没有值
                jsonObject.put(key, null);
            } else if (values.length == 1) { // 一个值
                jsonObject.put(key, values[0]);
            } else { // 多个值
                jsonObject.put(key, values);
            }
        }
        return jsonObject;
    }
}
