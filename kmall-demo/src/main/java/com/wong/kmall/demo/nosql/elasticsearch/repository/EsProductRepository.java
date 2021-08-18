package com.wong.kmall.demo.nosql.elasticsearch.repository;

import com.wong.kmall.demo.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author KaKinWong
 * @crate_time 2021/8/15 14:37
 * @description 商品ES操作类
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {
    /**
     * @param name     商品名称
     * @param subTitle 商品标题
     * @param keywords 商品关键词
     * @param page     分页信息
     * @return
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);
}
