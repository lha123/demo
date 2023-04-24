package com.example.demo.conf;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ApiModel(
        value = "响应",
        description = "响应数据"
)
public class Response<T> implements Serializable {
    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("描述")
    private String descr;
    @ApiModelProperty("状态(成功:true,失败:false)")
    private Boolean status = true;
    @ApiModelProperty("响应时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private LocalDateTime timestamp = LocalDateTime.now();
    @ApiModelProperty("响应数据")
    private T data;

    public Response() {
        this.code = HttpStatus.OK.value();
    }

    public Response(Integer code, String descr) {
        this.code = code;
        this.descr = descr;
    }

    public Response(Integer code, String descr, T data) {
        this.code = code;
        this.descr = descr;
        this.data = data;
    }

    public Response(Boolean status, Integer code, String descr, T data) {
        this.code = code;
        this.descr = descr;
        this.data = data;
        this.status = status;
    }



    public static <T> Response<T> failed(Integer code, String message) {
        return new Response(false, code, message, (Object)null);
    }


    public ResponseEntity<Response<T>> responseEntity() {
        return new ResponseEntity(this, HttpStatus.valueOf(this.getCode()));
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDescr() {
        return this.descr;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setDescr(final String descr) {
        this.descr = descr;
    }

    public void setStatus(final Boolean status) {
        this.status = status;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setData(final T data) {
        this.data = data;
    }




}

