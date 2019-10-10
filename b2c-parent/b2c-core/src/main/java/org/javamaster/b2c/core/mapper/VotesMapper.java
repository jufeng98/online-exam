package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Votes;
import org.javamaster.b2c.core.entity.VotesExample;

/**
 * 操纵投票表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface VotesMapper {
    long countByExample(VotesExample example);

    int deleteByExample(VotesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Votes record);

    int insertSelective(Votes record);

    List<Votes> selectByExample(VotesExample example);

    Votes selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Votes record, @Param("example") VotesExample example);

    int updateByExample(@Param("record") Votes record, @Param("example") VotesExample example);

    int updateByPrimaryKeySelective(Votes record);

    int updateByPrimaryKey(Votes record);
}