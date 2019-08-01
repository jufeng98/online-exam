package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.AuthoritiesMenus;
import org.javamaster.b2c.core.entity.AuthoritiesMenusExample;

/**
 * 操纵权限菜单表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface AuthoritiesMenusMapper {
    long countByExample(AuthoritiesMenusExample example);

    int deleteByExample(AuthoritiesMenusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AuthoritiesMenus record);

    int insertSelective(AuthoritiesMenus record);

    List<AuthoritiesMenus> selectByExample(AuthoritiesMenusExample example);

    AuthoritiesMenus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AuthoritiesMenus record, @Param("example") AuthoritiesMenusExample example);

    int updateByExample(@Param("record") AuthoritiesMenus record, @Param("example") AuthoritiesMenusExample example);

    int updateByPrimaryKeySelective(AuthoritiesMenus record);

    int updateByPrimaryKey(AuthoritiesMenus record);
}