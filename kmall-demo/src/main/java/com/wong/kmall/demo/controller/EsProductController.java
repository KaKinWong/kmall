package com.wong.kmall.demo.controller;

import com.wong.kmall.common.api.CommonPage;
import com.wong.kmall.common.api.CommonResult;
import com.wong.kmall.demo.nosql.elasticsearch.document.EsProduct;
import com.wong.kmall.demo.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/17 20:30
 * @description 搜索商品管理Controller
 */
@Api(tags = "搜索商品管理")
@RestController
@RequestMapping("/esProduct")
public class EsProductController {
    @Resource
    private EsProductService esProductService;

    @ApiOperation("导入所有数据库中商品到ES")
    @PostMapping("/importAll")
    public CommonResult<Integer> importAllList() {
        int count = esProductService.importAll();
        return CommonResult.success(count);
    }

    @ApiOperation(("根据id删除商品"))
    @GetMapping("delete/{id}")
    public CommonResult<Object> delete(@PathVariable Long id) {
        esProductService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation("根据id批量删除商品")
    @PostMapping("/delete/batch")
    public CommonResult<Object> delete(@RequestParam("idList") List<Long> idList) {
        esProductService.delete(idList);
        return CommonResult.success(null);
    }

    @ApiOperation("根据id创建商品")
    @PostMapping("/create/{id}")
    public CommonResult<EsProduct> create(@PathVariable Long id) {
        EsProduct esProduct = esProductService.create(id);
        if (esProduct == null) {
            return CommonResult.success(esProduct);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("简单搜索")
    @GetMapping("/search/simple")
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }
}
