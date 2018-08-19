/**
 * @Copyright (c) 2018/8/19, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.controller;

import com.wilson.rabbitmq.model.CreateOrderVo;
import com.wilson.rabbitmq.service.OrderService;
import com.wilson.rabbitmq.utils.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单控制器
 * @author wilson wei
 * @version 1.0
 * @since 2018/8/19 11:17
 */
@Api(value = "1.0")
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "模拟用户下单", notes = "下单后三十分钟未支付，系统自动取消订单")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String sendDelayMessage(@ApiParam(name = "订单", value = "order", required = true) @RequestBody CreateOrderVo order, HttpServletRequest request) {

        orderService.createOrder(order);
        String token = request.getHeaders("token").nextElement().toString();
        System.out.println(token);
        return "success";
    }
}
