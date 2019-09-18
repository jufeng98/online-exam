package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.LearnsRecord;
import org.javamaster.b2c.core.entity.LearnsRecordExample;

/**
 * 操纵学习记录表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface LearnsRecordMapper {
    long countByExample(LearnsRecordExample example);

    int deleteByExample(LearnsRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LearnsRecord record);

    int insertSelective(LearnsRecord record);

    List<LearnsRecord> selectByExample(LearnsRecordExample example);

    LearnsRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LearnsRecord record, @Param("example") LearnsRecordExample example);

    int updateByExample(@Param("record") LearnsRecord record, @Param("example") LearnsRecordExample example);

    int updateByPrimaryKeySelective(LearnsRecord record);

    int updateByPrimaryKey(LearnsRecord record);
}