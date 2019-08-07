package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Topics;
import org.javamaster.b2c.core.entity.TopicsExample;

/**
 * 操纵主题表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface TopicsMapper {
    long countByExample(TopicsExample example);

    int deleteByExample(TopicsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Topics record);

    int insertSelective(Topics record);

    List<Topics> selectByExampleWithBLOBs(TopicsExample example);

    List<Topics> selectByExample(TopicsExample example);

    Topics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Topics record, @Param("example") TopicsExample example);

    int updateByExampleWithBLOBs(@Param("record") Topics record, @Param("example") TopicsExample example);

    int updateByExample(@Param("record") Topics record, @Param("example") TopicsExample example);

    int updateByPrimaryKeySelective(Topics record);

    int updateByPrimaryKeyWithBLOBs(Topics record);

    int updateByPrimaryKey(Topics record);
}