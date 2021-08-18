package com.wong.kmall.demo.service;

import com.wong.kmall.demo.dao.EsProductDAO;
import com.wong.kmall.demo.nosql.elasticsearch.document.EsProduct;
import com.wong.kmall.demo.nosql.elasticsearch.repository.EsProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/15 15:32
 * @description 商品搜索管理Service
 */
@Service
public class EsProductService {
    private static final Logger logger = LoggerFactory.getLogger(EsProductService.class);

    @Resource
    private EsProductDAO esProductDAO;
    @Resource
    private EsProductRepository esProductRepository;

    /**
     * 从数据库中导入所有的商品倒ES
     *
     * @return
     */
    public int importAll() {
        List<EsProduct> esProductList = esProductDAO.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = esProductRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    /**
     * 根据id删除商品
     *
     * @param id
     */
    public void delete(Long id) {
        esProductRepository.deleteById(id);
    }

    /**
     * 根据id创建商品
     *
     * @param id
     * @return
     */
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> esProductList = esProductDAO.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            result = esProductRepository.save(esProduct);
        }
        return result;
    }

    /**
     * 批量删除商品
     *
     * @param idList
     */
    public void delete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            List<EsProduct> esProductList = new ArrayList<>();
            for (Long id : idList) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            esProductRepository.deleteAll(esProductList);
        }
    }

    /**
     * 根据关键字搜索名称或副标题
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
