package com.example.demo.Aop;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.example.demo.conf.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {



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
    public Response exceptionHandler(Exception exception) {
        log.error("requestInfo:{}", JSONUtil.toJsonPrettyStr(TreadLocalVar.get()));
        log.error("exception:", exception);
        TreadLocalVar.remove();
        return Response.failed(500,exception.getMessage());
    }



    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public String asyncRequestTimeoutHandler(AsyncRequestTimeoutException e) {
        System.out.println("异步请求超时");
        return "304";
    }
}
