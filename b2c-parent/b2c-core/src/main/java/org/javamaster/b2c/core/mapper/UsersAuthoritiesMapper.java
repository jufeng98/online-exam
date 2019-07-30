package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.UsersAuthorities;
import org.javamaster.b2c.core.entity.UsersAuthoritiesExample;

/**
 * 操纵权限表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 * @date 2019/07/29 15:40:48
 */
public interface UsersAuthoritiesMapper {
    long countByExample(UsersAuthoritiesExample example);

    int deleteByExample(UsersAuthoritiesExample example);

    int deleteByPrimaryKey(String authority);

    int insert(UsersAuthorities record);

    int insertSelective(UsersAuthorities record);

    List<UsersAuthorities> selectByExample(UsersAuthoritiesExample example);

    UsersAuthorities selectByPrimaryKey(String authority);

    int updateByExampleSelective(@Param("record") UsersAuthorities record, @Param("example") UsersAuthoritiesExample example);

    int updateByExample(@Param("record") UsersAuthorities record, @Param("example") UsersAuthoritiesExample example);

    int updateByPrimaryKeySelective(UsersAuthorities record);

    int updateByPrimaryKey(UsersAuthorities record);
}