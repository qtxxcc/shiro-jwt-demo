package com.example.shiro.demo.demo.web;

import lombok.Data;

/**
 * <p>封装统一的返回结果</p>
 */
@Data
public class RestResult<T> {
    private int code;

    private String message;

    private T data;

    public int getCode() {
        return code;
    }

    public RestResult() {
    }


    public RestResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RestResult setCode(int code) {
        this.code = code;
        return this;
    }


}
