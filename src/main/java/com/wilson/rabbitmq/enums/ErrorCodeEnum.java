/**
 * @Copyright (c) 2018/8/19, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.enums;

import lombok.Data;

/**
 * 枚举注释
 * @author wilson wei
 * @version 1.0
 * @since 2018/8/19 11:47
 */
public enum ErrorCodeEnum {

    UNKNOWING_ERROR("000001", "服务器未知异常"),
    ;

    private String message;

    private String code;

    ErrorCodeEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
