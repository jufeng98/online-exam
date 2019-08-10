package org.javamaster.b2c.core.service.impl;

import org.javamaster.b2c.core.entity.AuthoritiesMenus;
import org.javamaster.b2c.core.entity.AuthoritiesMenusExample;
import org.javamaster.b2c.core.entity.Menus;
import org.javamaster.b2c.core.entity.MenusEntity;
import org.javamaster.b2c.core.entity.MenusExample;
import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.exception.BizException;
import org.javamaster.b2c.core.mapper.AuthoritiesMenusMapper;
import org.javamaster.b2c.core.mapper.MenusMapper;
import org.javamaster.b2c.core.mapper.MenusMapperExt;
import org.javamaster.b2c.core.model.vo.CreateMenusReqVo;
import org.javamaster.b2c.core.model.vo.CreateMenusResVo;
import org.javamaster.b2c.core.model.vo.DelMenusReqVo;
import org.javamaster.b2c.core.model.vo.DelMenusResVo;
import org.javamaster.b2c.core.model.vo.EditMenusReqVo;
import org.javamaster.b2c.core.model.vo.EditMenusResVo;
import org.javamaster.b2c.core.model.vo.GetAuthoritiesMenusListReqVo;
import org.javamaster.b2c.core.model.vo.GetUsersMenusListReqVo;
import org.javamaster.b2c.core.model.vo.MenusListResVo;
import org.javamaster.b2c.core.service.MenusService;
import org.javamaster.b2c.core.utils.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private MenusMapper menusMapper;
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
        if (reqVo.getShouldFilterNullSubMenus() == null) {
            reqVo.setShouldFilterNullSubMenus(true);
        }
        return getMenusList(authorities, reqVo.getShouldFilterNullSubMenus());
    }

    private MenusListResVo getMenusList(List<String> authorities, boolean shouldFilterNullSubMenus) {
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
            if (shouldFilterNullSubMenus) {
                if (CollectionUtils.isEmpty(menusEntity.getSubMenus())) {
                    iterator.remove();
                }
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateMenusResVo createMenus(CreateMenusReqVo reqVo) {
        Menus menus = new Menus();
        BeanUtils.copyProperties(reqVo.getCreateOrEditMenusForm(), menus);
        menusMapper.insertSelective(menus);
        Integer parentId = reqVo.getCreateOrEditMenusForm().getParentId();
        if (parentId != null && parentId != 0) {
            Menus menus1 = new Menus();
            menus1.setId(parentId);
            menus1.setHasSubMenu(true);
            menusMapper.updateByPrimaryKeySelective(menus1);
        }
        MenusExample menusExample = new MenusExample();
        MenusExample.Criteria criteria = menusExample.createCriteria();
        criteria.andNameEqualTo(reqVo.getCreateOrEditMenusForm().getName());
        CreateMenusResVo createMenusResVo = new CreateMenusResVo();
        createMenusResVo.setMenus(menusMapper.selectByExample(menusExample).get(0));
        return createMenusResVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DelMenusResVo delMenus(DelMenusReqVo reqVo) {
        MenusExample menusExample = new MenusExample();
        MenusExample.Criteria criteria = menusExample.createCriteria();
        criteria.andIdEqualTo(reqVo.getId());
        List<Menus> menus = menusMapper.selectByExample(menusExample);
        if (menus.isEmpty()) {
            throw new BizException(BizExceptionEnum.INVALID_REQ_PARAM);
        }
        Integer parentId = menus.get(0).getParentId();
        menusExample.clear();
        criteria = menusExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        menus = menusMapper.selectByExample(menusExample);
        if (menus.size() <= 1) {
            Menus menus1 = new Menus();
            menus1.setId(parentId);
            menus1.setHasSubMenu(false);
            menusMapper.updateByPrimaryKeySelective(menus1);
        }

        int affectRow = menusMapper.deleteByPrimaryKey(reqVo.getId());
        DelMenusResVo delMenusResVo = new DelMenusResVo();
        delMenusResVo.setAffectRow(affectRow);
        return delMenusResVo;
    }

    @Override
    public EditMenusResVo editMenus(EditMenusReqVo reqVo) {
        Menus menus = new Menus();
        BeanUtils.copyProperties(reqVo.getCreateOrEditMenusForm(), menus);
        int affectRow = menusMapper.updateByPrimaryKeySelective(menus);
        EditMenusResVo editMenusResVo = new EditMenusResVo();
        editMenusResVo.setAffectRow(affectRow);
        return editMenusResVo;
    }

}