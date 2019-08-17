package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.ExamsQuestions;
import org.javamaster.b2c.core.entity.ExamsQuestionsExample;

/**
 * 操纵考试试题表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface ExamsQuestionsMapper {
    long countByExample(ExamsQuestionsExample example);

    int deleteByExample(ExamsQuestionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExamsQuestions record);

    int insertSelective(ExamsQuestions record);

    List<ExamsQuestions> selectByExample(ExamsQuestionsExample example);

    ExamsQuestions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExamsQuestions record, @Param("example") ExamsQuestionsExample example);

    int updateByExample(@Param("record") ExamsQuestions record, @Param("example") ExamsQuestionsExample example);

    int updateByPrimaryKeySelective(ExamsQuestions record);

    int updateByPrimaryKey(ExamsQuestions record);
}