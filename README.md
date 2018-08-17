# RabbitMQ 延迟队列

> 结合rabbitmq 的延迟队列实现类似未支付订单三十分钟自动取消功能，替代传统的采用后台定时任务方式取消过期订单的功能

# 前言
> 了解一下rabbitmq中的基本术语
+ 交换器 exchange: 消息首先到达mq中的地方
+ 队列 queue: 消息最终落地mq的地方
+ 路由键 routingKey: 消息到达exchange之后怎么知道需要去哪个队列中，就是根据路由键去匹配
