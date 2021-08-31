package com.wong.kmall.demo.controller;

import com.wong.kmall.common.api.CommonResult;
import com.wong.kmall.demo.dto.OrderParam;
import com.wong.kmall.demo.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author KaKinWong
 * @crate_time 2021/8/30 23:36
 * @description 订单管理Controller
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
public class OmsPortalOrderController {
    @Resource
    private OmsPortalOrderService omsPortalOrderService;

    @ApiOperation("根据购物车信息生成订单")
    @PostMapping("/generateOrder")
    public CommonResult generateOrder(@RequestBody OrderParam orderParam) {
        return omsPortalOrderService.generateOrder(orderParam);
    }
}
