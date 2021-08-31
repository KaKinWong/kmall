package com.wong.kmall.demo.config;

import com.wong.kmall.demo.constant.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KaKinWong
 * @crate_time 2021/8/30 21:24
 * @description 消息队列配置
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 订单消息实际消费队列所绑定的交换机
     *
     * @return
     */
    @Bean
    public DirectExchange orderDirectExchange() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单延迟队列队列所绑定的交换机
     *
     * @return
     */
    @Bean
    public DirectExchange orderTtlDirec0tExchange() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单实际消费队列
     *
     * @return
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
    }

    /**
     * 订单延迟队列（死信队列）
     *
     * @return
     */
    @Bean
    public Queue orderTtlQueue() {
        return QueueBuilder
                .durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())
                .build();
    }

    /**
     * 将订单队列绑定到交换机
     *
     * @param orderDirectExchange
     * @param orderQueue
     * @return
     */
    @Bean
    public Binding orderBinding(DirectExchange orderDirectExchange, Queue orderQueue) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirectExchange)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }

    /**
     * 将订单延迟队列绑定到交换机
     *
     * @param orderTtlDirec0tExchange
     * @param orderTtlQueue
     * @return
     */
    @Bean
    public Binding orderTtlBinding(DirectExchange orderTtlDirec0tExchange, Queue orderTtlQueue) {
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirec0tExchange)
                .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
    }
}
