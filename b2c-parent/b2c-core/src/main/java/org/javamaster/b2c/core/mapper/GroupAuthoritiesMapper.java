package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.GroupAuthorities;
import org.javamaster.b2c.core.entity.GroupAuthoritiesExample;

/**
 * 操纵权限组权限表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface GroupAuthoritiesMapper {
    long countByExample(GroupAuthoritiesExample example);

    int deleteByExample(GroupAuthoritiesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GroupAuthorities record);

    int insertSelective(GroupAuthorities record);

    List<GroupAuthorities> selectByExample(GroupAuthoritiesExample example);

    GroupAuthorities selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GroupAuthorities record, @Param("example") GroupAuthoritiesExample example);

    int updateByExample(@Param("record") GroupAuthorities record, @Param("example") GroupAuthoritiesExample example);

    int updateByPrimaryKeySelective(GroupAuthorities record);

    int updateByPrimaryKey(GroupAuthorities record);
}