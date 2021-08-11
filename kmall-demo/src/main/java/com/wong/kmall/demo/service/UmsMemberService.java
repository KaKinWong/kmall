package com.wong.kmall.demo.service;

import com.wong.kmall.common.api.CommonResult;
import com.wong.kmall.common.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author KaKinWong
 * @crate_time 2021/7/28 22:35
 * @description 会员管理Service
 */
@Service
public class UmsMemberService {
    @Resource
    private RedisService redisService;
    @Value("${redis.key.prefix.auth-code}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.auth-code.seconds}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    /**
     * 生成验证码
     *
     * @param telephone
     * @return
     */
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        String key = REDIS_KEY_PREFIX_AUTH_CODE + telephone;
        String value = stringBuilder.toString();
        redisService.set(key, value);
        redisService.expire(key, AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(value, "获取验证码成功");
    }

    /**
     * 判断验证码和手机号码是否匹配
     *
     * @param telephone
     * @param authCode
     * @return
     */
    public CommonResult verifyAuthCode(String telephone, String authCode) {
        if (!StringUtils.hasLength(authCode)) {
            return CommonResult.failed("请输入验证码");
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        boolean result = authCode.equals(realAuthCode);
        if (result) {
            return CommonResult.success(null, "验证码校验成功");
        } else {
            return CommonResult.failed("验证码不正确");
        }
    }
}
