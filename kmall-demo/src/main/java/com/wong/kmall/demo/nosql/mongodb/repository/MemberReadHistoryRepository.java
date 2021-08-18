package com.wong.kmall.demo.nosql.mongodb.repository;

import com.wong.kmall.demo.nosql.mongodb.document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/18 21:28
 * @description 会员商品浏览历史Repository
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {
    /**
     * 根据会员id按时间倒序获取浏览记录
     *
     * @param memberId 会员id
     * @return
     */
    List<MemberReadHistory> findByIdOrderByCreateTimeDesc(Long memberId);
}
