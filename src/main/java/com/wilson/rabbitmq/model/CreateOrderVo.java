/**
 * @Copyright (c) 2018/8/19, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下订单
 * @author wilson wei
 * @version 1.0
 * @since 2018/8/19 11:19
 */
@Data
public class CreateOrderVo {

    @ApiModelProperty(name = "所购买图书id", required = true, dataType = "Long", example = "1008")
    private Long bookId;

    @ApiModelProperty(name = "所购买图书名称", required = true, dataType = "String", example = "红楼梦")
    private String bookName;

    @ApiModelProperty(name = "图书价格", hidden = true)
    private String price;

    @ApiModelProperty(name = "订单编号", hidden = true)
    private String orderNo;
}
