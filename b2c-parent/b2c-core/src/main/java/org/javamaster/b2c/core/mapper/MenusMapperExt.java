package org.javamaster.b2c.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.javamaster.b2c.core.entity.Menus;

import java.util.List;

/**
 * @author yudong
 * @date 2019/7/26
 */
public interface MenusMapperExt {

    List<Menus> findTopMenus();

    List<Menus> findSubMenus(@Param("parentId") int parentId);

}
