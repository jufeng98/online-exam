package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.GroupMembers;
import org.javamaster.b2c.core.entity.GroupMembersExample;

/**
 * 操纵权限组用户表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface GroupMembersMapper {
    long countByExample(GroupMembersExample example);

    int deleteByExample(GroupMembersExample example);

    int insert(GroupMembers record);

    int insertSelective(GroupMembers record);

    List<GroupMembers> selectByExample(GroupMembersExample example);

    int updateByExampleSelective(@Param("record") GroupMembers record, @Param("example") GroupMembersExample example);

    int updateByExample(@Param("record") GroupMembers record, @Param("example") GroupMembersExample example);
}