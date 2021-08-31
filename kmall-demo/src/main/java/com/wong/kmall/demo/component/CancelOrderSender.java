package com.wong.kmall.demo.component;

import com.wong.kmall.demo.constant.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author KaKinWong
 * @crate_time 2021/8/30 22:18
 * @description 取消订单消息的发出者
 */
@Component
public class CancelOrderSender {
    private static Logger logger = LoggerFactory.getLogger(CancelOrderSender.class);

    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId, long delayTimes) {
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, message -> {
            // 消息设置存活时间
            message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
            return message;
        });
        logger.info("send delay message orderId:[{}]", orderId);
    }
}
