package com.wong.kmall.demo.controller;

import com.wong.kmall.common.api.CommonPage;
import com.wong.kmall.common.api.CommonResult;
import com.wong.kmall.demo.service.PmsBrandService;
import com.wong.kmall.entity.PmsBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/7/24 23:12
 * @description 品牌管理Controller
 */
@Api(tags = "商品品牌管理")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
    private static final Logger logger = LoggerFactory.getLogger(PmsBrandController.class);
    @Resource
    private PmsBrandService pmsBrandService;

    @ApiOperation("获取所有品牌列表")
    @GetMapping(value = "listAll")
    @PreAuthorize("hasAnyAuthority('pms:brand:read')")
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(pmsBrandService.listAllBrand());
    }

    @ApiOperation("添加品牌")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:brand:create')")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = pmsBrandService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            logger.debug("createBrand success:{}", pmsBrand);
        } else {
            commonResult = CommonResult.failed("操作失败");
            logger.debug("createBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    @ApiOperation("更新指定id品牌信息")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:brand:update')")
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto, BindingResult result) {
        CommonResult commonResult;
        int count = pmsBrandService.updateBrand(id, pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
            logger.debug("updateBrand success:{}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            logger.debug("updateBrand failed:{}", pmsBrandDto);
        }
        return commonResult;
    }

    @ApiOperation("删除指定id的品牌")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        int count = pmsBrandService.deleteBrand(id);
        if (count == 1) {
            logger.debug("deleteBrand success :id={}", id);
            return CommonResult.success(null);
        } else {
            logger.debug("deleteBrand failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }

    @ApiOperation("分页查询品牌列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<PmsBrand> brandList = pmsBrandService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @ApiOperation("获取指定id的品牌详情")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(pmsBrandService.getBrand(id));
    }
}
