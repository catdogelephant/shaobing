package com.zhumuchang.dongqu.commons.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author sx
 * @Description 取消订单死信队列配置类
 * @Date 2022/10/20 9:53
 */
@Configuration
public class CancelOrderQueueConfig {

    /**
     * 正常交换机
     */
    private static final String NORMAL_EXCHANGE = "normalExchange";

    /**
     * 取消订单正常队列
     */
    private static final String CANCEL_ORDER_NORMAL_QUEUE = "cancelOrderNormalQueue";

    /**
     * 取消订单死信队列
     */
    private static final String CANCEL_ORDER_QUEUE = "cancelOrderQueue";

    /**
     * 取消订单死信交换机
     */
    private static final String CANCEL_ORDER_EXCHANGE = "cancelOrderExchange";

    /**
     * 声明正常交换机
     */
    @Bean("normalExchange")
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    /**
     * 声明取消订单正常队列，并绑定取消订单死信交换机
     */
    @Bean("cancelOrderNormalQueue")
    public Queue cancelOrderNormalQueue() {
        Map<String, Object> arguments = new HashMap<>(3);
        // 声明当前队列绑定的死信交换机
        arguments.put("x-dead-letter-exchange", CANCEL_ORDER_EXCHANGE);
        // 声明当前队列的死信路由 key
        arguments.put("x-dead-letter-routing-key", "cancelOrderKey");
        // 没有声明 TTL 属性
        return QueueBuilder.durable(CANCEL_ORDER_NORMAL_QUEUE).withArguments(arguments).build();
    }

    /**
     * 取消订单正常队列绑定正常交换机
     */
    @Bean
    public Binding cancelOrderQueueBindingNormalExchange(@Qualifier("cancelOrderNormalQueue") Queue cancelOrderNormalQueue,
                                                         @Qualifier("normalExchange") DirectExchange normalExchange) {
        return BindingBuilder.bind(cancelOrderNormalQueue).to(normalExchange).with("normalKey");
    }

    /**
     * 创建死信队列
     */
    @Bean("cancelOrderQueue")
    public Queue cancelOrderQueue() {
        return new Queue(CANCEL_ORDER_QUEUE);
    }

    /**
     * 声明取消订单死信交换机
     */
    @Bean("cancelOrderExchange")
    public DirectExchange cancelOrderExchange() {
        return new DirectExchange(CANCEL_ORDER_EXCHANGE);
    }

    /**
     * 死信队列绑定死信交换机
     */
    @Bean
    public Binding cancelOrderQueueBindingCancelOrderExchanel(@Qualifier("cancelOrderQueue") Queue cancelOrderQueue,
                                                              @Qualifier("cancelOrderExchange") DirectExchange cancelOrderExchange) {
        return BindingBuilder.bind(cancelOrderQueue).to(cancelOrderExchange).with("cancelOrderKey");
    }
}
