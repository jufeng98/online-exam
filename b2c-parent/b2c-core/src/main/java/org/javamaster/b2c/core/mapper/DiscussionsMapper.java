package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Discussions;
import org.javamaster.b2c.core.entity.DiscussionsExample;

/**
 * 操纵讨论表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface DiscussionsMapper {
    long countByExample(DiscussionsExample example);

    int deleteByExample(DiscussionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Discussions record);

    int insertSelective(Discussions record);

    List<Discussions> selectByExample(DiscussionsExample example);

    Discussions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Discussions record, @Param("example") DiscussionsExample example);

    int updateByExample(@Param("record") Discussions record, @Param("example") DiscussionsExample example);

    int updateByPrimaryKeySelective(Discussions record);

    int updateByPrimaryKey(Discussions record);
}