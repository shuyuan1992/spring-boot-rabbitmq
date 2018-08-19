/**
 * @Copyright (c) 2018/8/19, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.exception;

import com.wilson.rabbitmq.enums.ErrorCodeEnum;

/**
 * 系统类异常，如数据库崩溃，mq崩溃
 * @author wilson wei
 * @version 1.0
 * @since 2018/8/19 11:44
 */
public class SystemException extends RuntimeException {

    /** 错误码 */
    private ErrorCodeEnum errorCode;

    /** 是否打印ERROR 级别日志 */
    private Boolean printError;

    public SystemException(ErrorCodeEnum errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.printError = true;
    }
}
