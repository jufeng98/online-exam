package org.javamaster.b2c.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Menus;

import java.util.List;

/**
 * @author
 * @date
 */
public interface MenusMapperExt {

    List<Menus> findTopMenus();

    List<Menus> findSubMenus(@Param("parentId") int parentId);

}
