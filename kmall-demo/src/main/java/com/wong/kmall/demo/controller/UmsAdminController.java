package com.wong.kmall.demo.controller;

import com.wong.kmall.common.api.CommonResult;
import com.wong.kmall.demo.dto.UmsAdminLoginParam;
import com.wong.kmall.demo.service.UmsAdminService;
import com.wong.kmall.entity.UmsAdmin;
import com.wong.kmall.entity.UmsPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KaKinWong
 * @crate_time 2021/8/9 0:37
 * @description 后台用户管理
 */
@Api(tags = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Resource
    private UmsAdminService umsAdminService;
    @Value("${jwt.token-head}")
    private String tokenHead;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation("登录以后返回token")
    @PostMapping("/login")
    public CommonResult login(@Valid @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        String token = umsAdminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取用户所有权限（包括+-权限）")
    @GetMapping("/permission/{adminId}")
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId) {
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }
}
