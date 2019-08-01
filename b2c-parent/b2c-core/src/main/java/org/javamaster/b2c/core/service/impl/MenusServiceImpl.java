package org.javamaster.b2c.core.service.impl;

import org.javamaster.b2c.core.entity.AuthoritiesMenus;
import org.javamaster.b2c.core.entity.AuthoritiesMenusExample;
import org.javamaster.b2c.core.entity.Menus;
import org.javamaster.b2c.core.entity.MenusEntity;
import org.javamaster.b2c.core.mapper.AuthoritiesMenusMapper;
import org.javamaster.b2c.core.mapper.MenusMapperExt;
import org.javamaster.b2c.core.model.vo.GetAuthoritiesMenusListReqVo;
import org.javamaster.b2c.core.model.vo.GetUsersMenusListReqVo;
import org.javamaster.b2c.core.model.vo.MenusListResVo;
import org.javamaster.b2c.core.service.MenusService;
import org.javamaster.b2c.core.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yudong
 * @date 2019/7/22
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class MenusServiceImpl implements MenusService {

    @Autowired
    private MenusMapperExt menusMapperExt;
    @Autowired
    private AuthoritiesMenusMapper authoritiesMenusMapper;

    @Override
    public MenusListResVo getMenusList(GetUsersMenusListReqVo reqVo, UserDetails userDetails) {
        List<String> authorities = null;
        if (userDetails != null) {
            authorities = userDetails.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList());
        }
        return getMenusList(authorities);
    }

    private MenusListResVo getMenusList(List<String> authorities) {
        List<Menus> topMenus = menusMapperExt.findTopMenus();
        List<MenusEntity> topMenusEntities = ListUtils.copyList(topMenus, MenusEntity.class);

        List<Integer> menusIds = null;
        if (authorities != null) {
            AuthoritiesMenusExample authoritiesMenusExample = new AuthoritiesMenusExample();
            AuthoritiesMenusExample.Criteria criteria = authoritiesMenusExample.createCriteria();
            criteria.andAuthorityIn(authorities);
            List<AuthoritiesMenus> authoritiesMenus = authoritiesMenusMapper.selectByExample(authoritiesMenusExample);
            menusIds = authoritiesMenus.stream().map(AuthoritiesMenus::getMenusId).collect(Collectors.toList());
        }
        Iterator<MenusEntity> iterator = topMenusEntities.iterator();
        while (iterator.hasNext()) {
            MenusEntity menusEntity = iterator.next();
            fillSubMenus(menusEntity, menusIds);
            if (CollectionUtils.isEmpty(menusEntity.getSubMenus())) {
                iterator.remove();
            }
        }
        MenusListResVo menusListResVo = new MenusListResVo();
        menusListResVo.setMenusEntities(topMenusEntities);
        return menusListResVo;
    }

    private void fillSubMenus(MenusEntity menusEntity, List<Integer> menusIds) {
        if (!menusEntity.getHasSubMenu()) {
            return;
        }
        List<Menus> subMenus = menusMapperExt.findSubMenus(menusEntity.getId());
        List<MenusEntity> menusEntities = ListUtils.copyList(subMenus, MenusEntity.class);
        Iterator<MenusEntity> iterator = menusEntities.iterator();
        while (iterator.hasNext()) {
            MenusEntity entity = iterator.next();
            if (menusIds != null && !menusIds.contains(entity.getId())) {
                iterator.remove();
            } else {
                fillSubMenus(entity, menusIds);
            }
        }
        menusEntity.setSubMenus(menusEntities);
    }

    @Override
    public MenusListResVo getAuthoritiesMenusList(GetAuthoritiesMenusListReqVo reqVo) {
        AuthoritiesMenusExample authoritiesMenusExample = new AuthoritiesMenusExample();
        AuthoritiesMenusExample.Criteria criteria = authoritiesMenusExample.createCriteria();
        criteria.andAuthorityIn(Arrays.asList(reqVo.getAuthority()));
        List<AuthoritiesMenus> authoritiesMenus = authoritiesMenusMapper.selectByExample(authoritiesMenusExample);
        List<MenusEntity> menusEntities = authoritiesMenus.stream().map(authoritiesMenus1 -> {
            MenusEntity menusEntity = new MenusEntity();
            menusEntity.setId(authoritiesMenus1.getMenusId());
            return menusEntity;
        }).collect(Collectors.toList());
        MenusListResVo menusListResVo = new MenusListResVo();
        menusListResVo.setMenusEntities(menusEntities);
        return menusListResVo;
    }
}