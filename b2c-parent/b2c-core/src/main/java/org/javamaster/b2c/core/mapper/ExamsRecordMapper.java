package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.ExamsRecord;
import org.javamaster.b2c.core.entity.ExamsRecordExample;

/**
 * 操纵考试记录表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface ExamsRecordMapper {
    long countByExample(ExamsRecordExample example);

    int deleteByExample(ExamsRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamsRecord record);

    int insertSelective(ExamsRecord record);

    List<ExamsRecord> selectByExample(ExamsRecordExample example);

    ExamsRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamsRecord record, @Param("example") ExamsRecordExample example);

    int updateByExample(@Param("record") ExamsRecord record, @Param("example") ExamsRecordExample example);

    int updateByPrimaryKeySelective(ExamsRecord record);

    int updateByPrimaryKey(ExamsRecord record);
}