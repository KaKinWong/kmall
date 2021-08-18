package com.wong.kmall.demo.service;

import com.wong.kmall.demo.nosql.mongodb.document.MemberReadHistory;
import com.wong.kmall.demo.nosql.mongodb.repository.MemberReadHistoryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/18 21:43
 * @description 会员浏览记录管理Service
 */
@Service
public class MemberReadHistoryService {
    @Resource
    private MemberReadHistoryRepository memberReadHistoryRepository;

    /**
     * 生产浏览记录
     *
     * @param memberReadHistory
     * @return
     */
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(LocalDateTime.now());
        memberReadHistoryRepository.save(memberReadHistory);
        return 1;
    }

    /**
     * 批量删除浏览记录
     *
     * @param idList
     * @return
     */
    public int delete(List<String> idList) {
        List<MemberReadHistory> deleteList = new ArrayList<>();
        for (String id : idList) {
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            deleteList.add(memberReadHistory);
        }
        memberReadHistoryRepository.deleteAll(deleteList);
        return deleteList.size();
    }

    /**
     * 获取用户浏览历史记录
     *
     * @param memberId
     * @return
     */
    public List<MemberReadHistory> list(Long memberId) {
        return memberReadHistoryRepository.findByIdOrderByCreateTimeDesc(memberId);
    }
}
