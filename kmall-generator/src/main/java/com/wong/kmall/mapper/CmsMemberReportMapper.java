package com.wong.kmall.mapper;

import com.wong.kmall.entity.CmsMemberReport;
import com.wong.kmall.entity.CmsMemberReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsMemberReportMapper {
    long countByExample(CmsMemberReportExample example);

    int deleteByExample(CmsMemberReportExample example);

    int insert(CmsMemberReport record);

    int insertSelective(CmsMemberReport record);

    List<CmsMemberReport> selectByExample(CmsMemberReportExample example);

    int updateByExampleSelective(@Param("record") CmsMemberReport record, @Param("example") CmsMemberReportExample example);

    int updateByExample(@Param("record") CmsMemberReport record, @Param("example") CmsMemberReportExample example);
}