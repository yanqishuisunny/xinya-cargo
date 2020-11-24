package com.commom.exception;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult<T extends Object> implements Serializable {

    /**
     * 请求状态（成功或失败）
     */
    @JSONField(ordinal = 1)
    private String status;

    /**
     * 编码
     */
    @JSONField(ordinal = 2)
    private int code;
    /**
     * 提示消息
     */
    @JSONField(ordinal = 3)
    private String message;
    /***
     * 数据对象
     */
    @JSONField(ordinal = 4)
    private T data;


    public RestResult(int code, String message) {
        this.code = code;
        this.message = message;

    }

    public RestResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RestResult(String status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
