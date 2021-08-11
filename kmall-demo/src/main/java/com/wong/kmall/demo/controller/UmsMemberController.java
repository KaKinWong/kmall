package com.wong.kmall.demo.controller;

import com.wong.kmall.common.api.CommonResult;
import com.wong.kmall.demo.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author KaKinWong
 * @crate_time 2021/7/28 23:17
 * @description 会员登录注册管理Controller
 */
@Api(tags = "会员登录注册管理")
@RestController
@RequestMapping("/sso")
public class UmsMemberController {
    @Resource
    private UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @GetMapping(value = "/get-auth-code")
    public CommonResult getAuthCode(@RequestParam String telephone) {
        return umsMemberService.generateAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @PostMapping(value = "/verify-auth-code")
    public CommonResult verifyAuthCode(@RequestParam String telephone, @RequestParam String authCode) {
        return umsMemberService.verifyAuthCode(telephone, authCode);
    }
}
