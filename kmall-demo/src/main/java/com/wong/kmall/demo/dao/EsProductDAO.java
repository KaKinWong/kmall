package com.wong.kmall.demo.dao;

import com.wong.kmall.demo.nosql.elasticsearch.document.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/15 15:48
 * @description 搜索系统重的商品管理自定义DAO
 */
public interface EsProductDAO {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
