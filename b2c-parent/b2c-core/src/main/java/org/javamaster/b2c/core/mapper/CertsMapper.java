package org.javamaster.b2c.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Certs;
import org.javamaster.b2c.core.entity.CertsExample;

/**
 * 操纵证书表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public interface CertsMapper {
    long countByExample(CertsExample example);

    int deleteByExample(CertsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Certs record);

    int insertSelective(Certs record);

    List<Certs> selectByExample(CertsExample example);

    Certs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Certs record, @Param("example") CertsExample example);

    int updateByExample(@Param("record") Certs record, @Param("example") CertsExample example);

    int updateByPrimaryKeySelective(Certs record);

    int updateByPrimaryKey(Certs record);
}