package com.wong.kmall.demo.component;

import com.rabbitmq.client.Channel;
import com.wong.kmall.demo.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author KaKinWong
 * @crate_time 2021/8/30 22:36
 * @description 取消订单消息的处理者
 */
@Component
public class CancelOrderReceiver {
    private static Logger logger = LoggerFactory.getLogger(CancelOrderReceiver.class);

    @Resource
    private OmsPortalOrderService omsPortalOrderService;

    @RabbitListener(queues = "kmall.order.cancel")
    public void handle(Long orderId, Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            logger.error("取消订单队列手动ask失败", e);
        }
        omsPortalOrderService.cancelOrder(orderId);
    }
}
