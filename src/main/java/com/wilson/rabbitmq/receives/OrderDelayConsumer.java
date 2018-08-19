/**
 * @Copyright (c) 2018/8/19, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.receives;

import com.alibaba.fastjson.JSONObject;
import com.wilson.rabbitmq.model.CreateOrderVo;
import com.wilson.rabbitmq.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 类注释
 * @author wilson wei
 * @version 1.0
 * @since 2018/8/19 11:53
 */
@Component
@RabbitListener(queues = "process_queue")
@Slf4j
public class OrderDelayConsumer {

    @RabbitHandler
    public void orderConsumer(Message message){

        // 接收到处理队列中的消息的，就是指定时间过期的消息
        // 这里处理每一条消息中的订单编号，去查询对应的订单支付状态，如果处于未支付状态，就取消用户的订单
        try {
            CreateOrderVo orderVo = JSONObject.parseObject(message.getContent(), CreateOrderVo.class);
            // 获取订单编号，去查询对应的支付结果
            log.info("订单编号为: {}", orderVo.getOrderNo());
        } catch (Exception e) {
            log.error("订单消息解析异常，请检查消息格式是否正确", message.getContent());
        }
    }
}
