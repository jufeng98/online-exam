package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.KnowledgePoints;
import org.javamaster.b2c.core.entity.KnowledgePointsExample;

/**
 * 操纵知识点表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface KnowledgePointsMapper {
    long countByExample(KnowledgePointsExample example);

    int deleteByExample(KnowledgePointsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KnowledgePoints record);

    int insertSelective(KnowledgePoints record);

    List<KnowledgePoints> selectByExample(KnowledgePointsExample example);

    KnowledgePoints selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KnowledgePoints record, @Param("example") KnowledgePointsExample example);

    int updateByExample(@Param("record") KnowledgePoints record, @Param("example") KnowledgePointsExample example);

    int updateByPrimaryKeySelective(KnowledgePoints record);

    int updateByPrimaryKey(KnowledgePoints record);
}