package com.wong.kmall.demo.service;

import com.github.pagehelper.PageHelper;
import com.wong.kmall.entity.PmsBrand;
import com.wong.kmall.entity.PmsBrandExample;
import com.wong.kmall.mapper.PmsBrandMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/7/24 23:23
 * @description 商品品牌管理Service
 */
@Service
public class PmsBrandService {
    @Resource
    private PmsBrandMapper pmsBrandMapper;

    public List<PmsBrand> listAllBrand() {
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    public int createBrand(PmsBrand brand) {
        return pmsBrandMapper.insertSelective(brand);
    }

    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return pmsBrandMapper.updateByPrimaryKeySelective(brand);
    }

    public int deleteBrand(Long id) {
        return pmsBrandMapper.deleteByPrimaryKey(id);
    }

    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    public PmsBrand getBrand(Long id) {
        return pmsBrandMapper.selectByPrimaryKey(id);
    }
}
