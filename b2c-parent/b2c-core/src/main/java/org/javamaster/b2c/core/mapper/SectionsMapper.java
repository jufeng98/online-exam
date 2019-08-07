package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Sections;
import org.javamaster.b2c.core.entity.SectionsExample;

/**
 * 操纵章节表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface SectionsMapper {
    long countByExample(SectionsExample example);

    int deleteByExample(SectionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sections record);

    int insertSelective(Sections record);

    List<Sections> selectByExampleWithBLOBs(SectionsExample example);

    List<Sections> selectByExample(SectionsExample example);

    Sections selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sections record, @Param("example") SectionsExample example);

    int updateByExampleWithBLOBs(@Param("record") Sections record, @Param("example") SectionsExample example);

    int updateByExample(@Param("record") Sections record, @Param("example") SectionsExample example);

    int updateByPrimaryKeySelective(Sections record);

    int updateByPrimaryKeyWithBLOBs(Sections record);

    int updateByPrimaryKey(Sections record);
}