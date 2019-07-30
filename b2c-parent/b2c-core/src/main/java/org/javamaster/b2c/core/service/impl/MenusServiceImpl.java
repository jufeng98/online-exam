package org.javamaster.b2c.core.service.impl;

import org.javamaster.b2c.core.entity.Menus;
import org.javamaster.b2c.core.entity.MenusEntity;
import org.javamaster.b2c.core.mapper.MenusMapperExt;
import org.javamaster.b2c.core.model.vo.GetMenusListReqVo;
import org.javamaster.b2c.core.model.vo.GetMenusListResVo;
import org.javamaster.b2c.core.service.MenusService;
import org.javamaster.b2c.core.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yudong
 * @date 2019/7/22
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class MenusServiceImpl implements MenusService {

    @Autowired
    private MenusMapperExt menusMapperExt;

    @Override
    public GetMenusListResVo getMenusList(GetMenusListReqVo reqVo) {
        List<Menus> topMenus = menusMapperExt.findTopMenus();
        List<MenusEntity> topMenusEntities = ListUtils.copyList(topMenus, MenusEntity.class);
        for (MenusEntity menusEntity : topMenusEntities) {
            fillSubMenus(menusEntity);
        }
        GetMenusListResVo getMenusListResVo = new GetMenusListResVo();
        getMenusListResVo.setMenusEntities(topMenusEntities);
        return getMenusListResVo;
    }

    private void fillSubMenus(MenusEntity menusEntity) {
        if (!menusEntity.getHasSubMenu()) {
            return;
        }
        List<Menus> subMenus = menusMapperExt.findSubMenus(menusEntity.getId());
        List<MenusEntity> menusEntities = ListUtils.copyList(subMenus, MenusEntity.class);
        menusEntity.setSubMenus(menusEntities);
        for (MenusEntity subMenusEntity : menusEntities) {
            fillSubMenus(subMenusEntity);
        }
    }
}