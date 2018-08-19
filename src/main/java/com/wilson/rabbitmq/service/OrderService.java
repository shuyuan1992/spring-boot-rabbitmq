/**
 * @Copyright (c) 2018/8/19, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.service;

import com.alibaba.fastjson.JSONObject;
import com.wilson.rabbitmq.model.CreateOrderVo;
import com.wilson.rabbitmq.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 订单service
 * @author wilson wei
 * @version 1.0
 * @since 2018/8/19 11:24
 */
@Service
public class OrderService {

    @Autowired
    private DelaySendService delaySendService;

    /**
     * 功能描述 模拟用户购买图书下单
 * @param order 图书订单
     * @author wilson wei
     * @date 11:25 2018/8/19
     */
    public void createOrder(CreateOrderVo order) {

        // 参数校验，获取图书实际价格等

        // 根据规则生成订单编号，下单，插入数据库中

        // 拿到订单编号，发送下单消息到rabbitmq中
        String orderNo = "100000000201808191000982091";

        order.setOrderNo(orderNo);

        // 封装消息内容
        Message message = new Message();
        message.setContent(JSONObject.toJSONString(order));

        message.setId(1);
        // 三十分钟时效，自动取消订单
        message.setTtl(15, TimeUnit.MINUTES);
        delaySendService.sendDelayMessage(message);
    }
}
