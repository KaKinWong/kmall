package com.wong.kmall.demo.controller;

import com.wong.kmall.common.api.CommonResult;
import com.wong.kmall.demo.nosql.mongodb.document.MemberReadHistory;
import com.wong.kmall.demo.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/18 21:57
 * @description 会员商品浏览记录管理Controller
 */
@Api(tags = "会员商品浏览记录管理")
@RestController
@RequestMapping("/member/readHistory")
public class MemberREadHistoryController {
    @Resource
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation("创建浏览记录")
    @PostMapping("/create")
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除浏览记录")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam("idList") List<String> idList) {
        int count = memberReadHistoryService.delete(idList);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("展示浏览记录")
    @GetMapping("/list")
    public CommonResult<List<MemberReadHistory>> list(Long memberId) {
        List<MemberReadHistory> memberReadHistoryList = memberReadHistoryService.list(memberId);
        return CommonResult.success(memberReadHistoryList);
    }
}
