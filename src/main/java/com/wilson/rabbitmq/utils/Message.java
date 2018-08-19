/**
 * @Copyright (c) 2018/8/19, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 消息类
 * @author wilson wei
 * @version 1.0
 * @since 2018/8/19 9:49
 */
@Data
public class Message implements Serializable {

    @ApiModelProperty(name = "消息id")
    private Integer id;

    @ApiModelProperty(name = "消息内容", dataType = "String", required = true, example = "这是一个测试过期时间为3分钟的消息")
    private String content;

    @ApiModelProperty(name = "过期时间", dataType = "Long", required = true)
    private Long ttl;

    /**
     * 功能描述 设置消息过期时间
     * @author wilson wei
     * @date 11:38 2018/8/19
     */
    public void setTtl(int number, TimeUnit unit) {
        this.setTtl(unit.toMillis(number));
    }
}
