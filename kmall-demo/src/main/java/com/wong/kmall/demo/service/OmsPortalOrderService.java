package com.wong.kmall.demo.service;

import cn.hutool.json.JSONUtil;
import com.wong.kmall.common.api.CommonResult;
import com.wong.kmall.demo.component.CancelOrderSender;
import com.wong.kmall.demo.dto.OrderParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author KaKinWong
 * @crate_time 2021/8/30 23:13
 * @description 前台管理Service
 */
@Service
public class OmsPortalOrderService {
    private static Logger logger = LoggerFactory.getLogger(OmsPortalOrderService.class);

    @Resource
    private CancelOrderSender cancelOrderSender;

    /**
     * 根据提交信息生成订单
     *
     * @param orderParam
     * @return
     */
    @Transactional
    public CommonResult generateOrder(OrderParam orderParam) {
        logger.info("process generateOrder orderParam:[{}]", JSONUtil.toJsonStr(orderParam));
        // 执行一系列下单操作
        // 下单完成后开启一个延迟消息，用于没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null, "下单成功");
    }

    /**
     * 取消单个超时订单
     *
     * @param orderId
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 执行一系列取消订单操作
        logger.info("process cancelOrder orderId:[{}]", orderId);
    }

    private void sendDelayMessageCancelOrder(Long orderId) {
        // 订单超时时间
        long delayTimes = 30 * 1000;
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }
}
