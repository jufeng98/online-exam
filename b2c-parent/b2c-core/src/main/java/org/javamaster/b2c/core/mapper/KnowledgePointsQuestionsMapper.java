package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.KnowledgePointsQuestions;
import org.javamaster.b2c.core.entity.KnowledgePointsQuestionsExample;

/**
 * 操纵知识点题目表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface KnowledgePointsQuestionsMapper {
    long countByExample(KnowledgePointsQuestionsExample example);

    int deleteByExample(KnowledgePointsQuestionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KnowledgePointsQuestions record);

    int insertSelective(KnowledgePointsQuestions record);

    List<KnowledgePointsQuestions> selectByExample(KnowledgePointsQuestionsExample example);

    KnowledgePointsQuestions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KnowledgePointsQuestions record, @Param("example") KnowledgePointsQuestionsExample example);

    int updateByExample(@Param("record") KnowledgePointsQuestions record, @Param("example") KnowledgePointsQuestionsExample example);

    int updateByPrimaryKeySelective(KnowledgePointsQuestions record);

    int updateByPrimaryKey(KnowledgePointsQuestions record);
}