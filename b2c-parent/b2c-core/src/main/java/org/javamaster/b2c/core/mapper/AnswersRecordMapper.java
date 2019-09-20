package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.AnswersRecord;
import org.javamaster.b2c.core.entity.AnswersRecordExample;

/**
 * 操纵答题记录表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface AnswersRecordMapper {
    long countByExample(AnswersRecordExample example);

    int deleteByExample(AnswersRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AnswersRecord record);

    int insertSelective(AnswersRecord record);

    List<AnswersRecord> selectByExample(AnswersRecordExample example);

    AnswersRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AnswersRecord record, @Param("example") AnswersRecordExample example);

    int updateByExample(@Param("record") AnswersRecord record, @Param("example") AnswersRecordExample example);

    int updateByPrimaryKeySelective(AnswersRecord record);

    int updateByPrimaryKey(AnswersRecord record);
}