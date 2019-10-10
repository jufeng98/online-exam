package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Replys;
import org.javamaster.b2c.core.entity.ReplysExample;

/**
 * 操纵讨论表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface ReplysMapper {
    long countByExample(ReplysExample example);

    int deleteByExample(ReplysExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Replys record);

    int insertSelective(Replys record);

    List<Replys> selectByExample(ReplysExample example);

    Replys selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Replys record, @Param("example") ReplysExample example);

    int updateByExample(@Param("record") Replys record, @Param("example") ReplysExample example);

    int updateByPrimaryKeySelective(Replys record);

    int updateByPrimaryKey(Replys record);
}