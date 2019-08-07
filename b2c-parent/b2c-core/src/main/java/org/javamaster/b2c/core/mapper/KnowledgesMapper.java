package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Knowledges;
import org.javamaster.b2c.core.entity.KnowledgesExample;

/**
 * 操纵知识表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface KnowledgesMapper {
    long countByExample(KnowledgesExample example);

    int deleteByExample(KnowledgesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Knowledges record);

    int insertSelective(Knowledges record);

    List<Knowledges> selectByExample(KnowledgesExample example);

    Knowledges selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Knowledges record, @Param("example") KnowledgesExample example);

    int updateByExample(@Param("record") Knowledges record, @Param("example") KnowledgesExample example);

    int updateByPrimaryKeySelective(Knowledges record);

    int updateByPrimaryKey(Knowledges record);
}