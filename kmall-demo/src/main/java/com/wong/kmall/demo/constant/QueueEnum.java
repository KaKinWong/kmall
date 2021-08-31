package com.wong.kmall.demo.constant;

/**
 * @author KaKinWong
 * @crate_time 2021/8/30 20:56
 * @description 消息队列-枚举
 */
public enum QueueEnum {
    // 消息取消队列
    QUEUE_ORDER_CANCEL("kmall.order.direct", "kmall.order.cancel", "kmall.order.cancel"),
    // 消息取消ttl队列
    QUEUE_TTL_ORDER_CANCEL("kmall.order.direct.ttl", "kmall.order.cancel.ttl", "kmall.order.cancel.ttl");

    // 交换名称
    private String exchange;
    // 队列名称
    private String name;
    // 路由键
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

    public String getExchange() {
        return exchange;
    }

    public String getName() {
        return name;
    }

    public String getRouteKey() {
        return routeKey;
    }
}
