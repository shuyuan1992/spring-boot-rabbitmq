/**
 * @Copyright (c) 2018/8/17, Lianjia Group All Rights Reserved.
 */
package com.wilson.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq config
 * 所有声明的这些queue，exchange，以及绑定关系会在第一次发送消息时建立在rabbitmq中。
 * @author wilson wei
 * @version 1.0
 * @since 2018/8/17 17:22
 */
@Configuration
public class RabbitConfig {

    /******************************************************** 声明交换器 ************************************************************/
    /**
     * 该交换器为直接交换器，定义了绑定delay_queue队列
     */
    public final static String DELAY_EXCHANGE = "delay_exchange";

    /**
     * 延迟消息队列交换器
     *
     * @return org.springframework.amqp.core.Exchange
     * @author wilson wei
     * @date 17:55 2018/8/17
     */
    @Bean
    public Exchange delayExchange() {
        return ExchangeBuilder
                .directExchange(DELAY_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 该交换器为直接交换器，定义了绑定process_queue队列
     */
    public final static String PROCESS_EXCHANGE = "process_exchange";

    /**
     * 正常处理队列交换器
     *
     * @return org.springframework.amqp.core.Exchange
     * @author wilson wei
     * @date 17:55 2018/8/17
     */
    @Bean
    public Exchange processExchange() {
        return ExchangeBuilder
                .directExchange(PROCESS_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 该交换器为直接交换器，定义了绑定delay_queue_queue队列
     * 此交换器对应的队列设置为固定过期时间的，不是每个消息自带过期时间
     */
    public final static String DELAY_QUEUE_EXCHANGE = "delay_queue_exchange";

    /**
     * 延迟队列交换器
     * 此交换器对应的队列设置为固定过期时间的，不是每个消息自带过期时间
     *
     * @return org.springframework.amqp.core.Exchange
     * @author wilson wei
     * @date 17:55 2018/8/17
     */
    @Bean
    public Exchange delayQueueExchange() {
        return ExchangeBuilder
                .directExchange(DELAY_QUEUE_EXCHANGE)
                .durable(true)
                .build();
    }
    /******************************************************** 声明队列 ************************************************************/

    /**
     * 该队列没有直接消费者：而是定义了到达该队列的消息会在一定时间后过期，并在过期后进入到process queue队列中，每个消息都可以自定义自己的过期时间
     */
    public final static String DELAY_QUEUE_MSG = "delay_queue";

    /**
     * 延迟队列， 每个消息过期了都会自动发送给withArgument指定的exchange和指定的routing-key
     *
     * @return org.springframework.amqp.core.Queue
     * @author wilson wei
     * @date 18:02 2018/8/17
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE_MSG)
                // 延迟队列需要设置的消息过期后会发往的交换器名称
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                // 延迟队列需要设置的消息过期后会发往的路由键名称
                .withArgument("x-dead-letter-routing-key", DELAY_ROUTING_KEY)
                .build();
    }

    /**
     * 正常消费过期消息的队列，只有该队列有消费者
     */
    public final static String PROCESS_QUEUE = "process_queue";

    /**
     * 正常处理消息队列， 每个消息过期了都会自动路由到该队列绑定的交换器上
     * 普通队列声明只需要设置队列名称以及是否持久化等信息
     *
     * @return org.springframework.amqp.core.Queue
     * @author wilson wei
     * @date 18:02 2018/8/17
     */
    @Bean
    public Queue processQueue() {
        return QueueBuilder.durable(PROCESS_QUEUE)
                .build();
    }

    /**
     * 定义延迟队列，有固定过期时间的队列，指定名称为delay_queue_queue
     */
    public static final String DELAY_QUEUE_QUEUE = "delay_queue_queue";

    /**
     * 过期队列，指拥有固定过期时间的队列，其中的消息，每过30秒过期一次，全部转入到指定的x-dead-letter-xx 参数指定的交换器和路由键的队列中。
     *
     * @return org.springframework.amqp.core.Queue
     * @author wilson wei
     * @date 10:36 2018/8/19
     */
    @Bean
    public Queue delayQueueQueue() {
        return QueueBuilder.durable(DELAY_QUEUE_QUEUE)
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DELAY_ROUTING_KEY)
                .withArgument("x-message-ttl", 30000)
                .build();
    }
    /******************************************************** 声明路由键 ************************************************************/

    /**
     * 定义路由键，指定delay_queue 和 process_queue的路由规则
     */
    public final static String DELAY_ROUTING_KEY = "delay";

    /******************************************************** 声明绑定 ************************************************************/
    // 当队列声明完成，交换器声明完成后，就需要进行二者的绑定，并制定路由键绑定的路由规则

    /**
     * 延迟队列绑定到延迟交换器，并制定路由键为 DELAY_ROUTING_KEY
     * 谁绑定到谁，并指定路由键
     *
     * @return org.springframework.amqp.core.Binding
     * @author wilson wei
     * @date 18:12 2018/8/17
     */
    @Bean
    public Binding delayBiding() {
        return BindingBuilder.bind(delayQueue())
                .to(delayExchange())
                .with(DELAY_ROUTING_KEY).noargs();
    }

    /**
     * 正常处理队列绑定到正常处理交换器，并制定路由键为 DELAY_ROUTING_KEY
     * 谁绑定到谁，并指定路由键
     *
     * @return org.springframework.amqp.core.Binding
     * @author wilson wei
     * @date 18:12 2018/8/17
     */
    @Bean
    public Binding processBiding() {
        return BindingBuilder.bind(processQueue())
                .to(processExchange())
                .with(DELAY_ROUTING_KEY).noargs();
    }

    /**
     * 功能描述 将过期队列绑定到过期队列交换器上，并指定路由键
 * @return org.springframework.amqp.core.Binding
     * @author wilson wei
     * @date 10:39 2018/8/19
     */
    @Bean
    public Binding delayQueueBinding() {
        return BindingBuilder.bind(delayQueueQueue())
                .to(delayQueueExchange())
                .with(DELAY_ROUTING_KEY).noargs();
    }
    /*******************************************************  ********************************/


}
