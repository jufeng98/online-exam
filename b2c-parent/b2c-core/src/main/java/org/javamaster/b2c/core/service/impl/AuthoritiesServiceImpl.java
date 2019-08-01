package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.javamaster.b2c.core.entity.Authorities;
import org.javamaster.b2c.core.entity.AuthoritiesExample;
import org.javamaster.b2c.core.entity.AuthoritiesMenus;
import org.javamaster.b2c.core.entity.AuthoritiesMenusExample;
import org.javamaster.b2c.core.entity.UsersAuthorities;
import org.javamaster.b2c.core.entity.UsersAuthoritiesExample;
import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.exception.BizException;
import org.javamaster.b2c.core.mapper.AuthoritiesMapper;
import org.javamaster.b2c.core.mapper.AuthoritiesMenusMapper;
import org.javamaster.b2c.core.mapper.UsersAuthoritiesMapper;
import org.javamaster.b2c.core.model.vo.AuthOrUnAuthUsersReqVo;
import org.javamaster.b2c.core.model.vo.AuthOrUnAuthUsersResVo;
import org.javamaster.b2c.core.model.vo.ChangeAuthoritiesMenusReqVo;
import org.javamaster.b2c.core.model.vo.ChangeAuthoritiesMenusResVo;
import org.javamaster.b2c.core.model.vo.CreateAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.DelAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.DelAuthoritiesResVo;
import org.javamaster.b2c.core.model.vo.EditAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.FindAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.FindAuthoritiesResVo;
import org.javamaster.b2c.core.model.vo.FindUsersAuthoritiesReqVo;
import org.javamaster.b2c.core.service.AuthoritiesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yudong
 * @date 2019/7/28
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

    @Autowired
    private UsersAuthoritiesMapper usersAuthoritiesMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthoritiesMapper authoritiesMapper;
    @Autowired
    private AuthoritiesMenusMapper authoritiesMenusMapper;

    @Override
    public FindAuthoritiesResVo findAuthorities(FindAuthoritiesReqVo reqVo) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(reqVo.getUsername());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        FindAuthoritiesResVo findAuthoritiesResVo = new FindAuthoritiesResVo();
        findAuthoritiesResVo.setAuthorities(authorities);
        return findAuthoritiesResVo;
    }

    @Override
    public PageInfo<UsersAuthorities> findUsersAuthorities(FindUsersAuthoritiesReqVo reqVo) {
        UsersAuthoritiesExample usersAuthoritiesExample = new UsersAuthoritiesExample();
        UsersAuthoritiesExample.Criteria criteria = usersAuthoritiesExample.createCriteria();
        if (StringUtils.isNotBlank(reqVo.getAuthoritiesForm().getAuthority())) {
            criteria.andAuthorityLike("%" + reqVo.getAuthoritiesForm().getAuthority());
        }
        if (StringUtils.isNotBlank(reqVo.getAuthoritiesForm().getAuthorityName())) {
            criteria.andAuthorityNameLike(reqVo.getAuthoritiesForm().getAuthorityName() + "%");
        }
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "create_time desc");
        PageInfo<UsersAuthorities> pageInfo = new PageInfo(usersAuthoritiesMapper.selectByExample(usersAuthoritiesExample));
        return pageInfo;
    }

    @Override
    public AuthOrUnAuthUsersResVo authUsers(AuthOrUnAuthUsersReqVo reqVo) {
        List<Integer> affectRows = reqVo.getAuthorities().stream().map(authority -> {
            Authorities authorities = new Authorities();
            authorities.setUsername(reqVo.getUsername());
            authorities.setAuthority(authority);
            int affect = authoritiesMapper.insert(authorities);
            return affect;
        }).collect(Collectors.toList());
        AuthOrUnAuthUsersResVo authOrUnAuthUsersResVo = new AuthOrUnAuthUsersResVo();
        authOrUnAuthUsersResVo.setAffectRows(affectRows);
        return authOrUnAuthUsersResVo;
    }

    @Override
    public AuthOrUnAuthUsersResVo unAuthUsers(AuthOrUnAuthUsersReqVo reqVo) {
        List<Integer> affectRows = reqVo.getAuthorities().stream().map(authority -> {
            AuthoritiesExample authoritiesExample = new AuthoritiesExample();
            AuthoritiesExample.Criteria criteria = authoritiesExample.createCriteria();
            criteria.andUsernameEqualTo(reqVo.getUsername());
            criteria.andAuthorityEqualTo(authority);
            int affect = authoritiesMapper.deleteByExample(authoritiesExample);
            return affect;
        }).collect(Collectors.toList());
        AuthOrUnAuthUsersResVo authOrUnAuthUsersResVo = new AuthOrUnAuthUsersResVo();
        authOrUnAuthUsersResVo.setAffectRows(affectRows);
        return authOrUnAuthUsersResVo;
    }

    @Override
    public UsersAuthorities createAuthorities(CreateAuthoritiesReqVo reqVo) {
        if (usersAuthoritiesMapper.selectByPrimaryKey(reqVo.getCreateOrEditAuthoritiesForm().getAuthority()) != null) {
            throw new BizException(BizExceptionEnum.AUTHORITIES_EXISTS);
        }
        UsersAuthorities usersAuthorities = new UsersAuthorities();
        BeanUtils.copyProperties(reqVo.getCreateOrEditAuthoritiesForm(), usersAuthorities);
        usersAuthoritiesMapper.insertSelective(usersAuthorities);
        return usersAuthorities;
    }

    @Override
    public UsersAuthorities editAuthorities(EditAuthoritiesReqVo reqVo) {
        UsersAuthorities usersAuthorities = new UsersAuthorities();
        BeanUtils.copyProperties(reqVo.getCreateOrEditAuthoritiesForm(), usersAuthorities);
        int affect = usersAuthoritiesMapper.updateByPrimaryKeySelective(usersAuthorities);
        if (affect == 0) {
            throw new BizException(BizExceptionEnum.AUTHORITIES_NOT_EXISTS);
        }
        return usersAuthorities;
    }

    @Override
    public ChangeAuthoritiesMenusResVo changeAuthoritiesMenus(ChangeAuthoritiesMenusReqVo reqVo) {
        AuthoritiesMenusExample authoritiesMenusExample = new AuthoritiesMenusExample();
        AuthoritiesMenusExample.Criteria criteria = authoritiesMenusExample.createCriteria();
        criteria.andAuthorityEqualTo(reqVo.getAuthority());
        List<AuthoritiesMenus> authoritiesMenus = authoritiesMenusMapper.selectByExample(authoritiesMenusExample);
        List<Integer> dbMenusIds = authoritiesMenus.stream().map(AuthoritiesMenus::getMenusId).collect(Collectors.toList());

        List<Integer> newMenusIds = new ArrayList<>(reqVo.getMenusIds());
        newMenusIds.removeAll(dbMenusIds);
        List<Integer> affects = newMenusIds.stream().map(newMenusId -> {
            AuthoritiesMenus menus = new AuthoritiesMenus();
            menus.setAuthority(reqVo.getAuthority());
            menus.setMenusId(newMenusId);
            return authoritiesMenusMapper.insertSelective(menus);
        }).collect(Collectors.toList());

        List<Integer> delMenusIds = new ArrayList<>(dbMenusIds);
        delMenusIds.removeAll(reqVo.getMenusIds());
        List<Integer> affects1 = delMenusIds.stream().map(delMenusId -> {
            AuthoritiesMenusExample authoritiesMenusExample1 = new AuthoritiesMenusExample();
            AuthoritiesMenusExample.Criteria criteria1 = authoritiesMenusExample1.createCriteria();
            criteria1.andMenusIdEqualTo(delMenusId);
            return authoritiesMenusMapper.deleteByExample(authoritiesMenusExample1);
        }).collect(Collectors.toList());

        List<Integer> sumAffects = new ArrayList<>();
        sumAffects.addAll(affects);
        sumAffects.addAll(affects1);
        ChangeAuthoritiesMenusResVo changeAuthoritiesMenusResVo = new ChangeAuthoritiesMenusResVo();
        changeAuthoritiesMenusResVo.setSumAffects(sumAffects);
        return changeAuthoritiesMenusResVo;
    }

    @Override
    public DelAuthoritiesResVo delAuthorities(DelAuthoritiesReqVo reqVo) {
        AuthoritiesExample authoritiesExample = new AuthoritiesExample();
        AuthoritiesExample.Criteria authoritiesExampleCriteria = authoritiesExample.createCriteria();
        authoritiesExampleCriteria.andAuthorityEqualTo(reqVo.getAuthority());
        List<Authorities> authorities = authoritiesMapper.selectByExample(authoritiesExample);
        if (!authorities.isEmpty()) {
            throw new BizException(BizExceptionEnum.AUTHORITIES_ASSOCIATE_USER);
        }
        int affect1 = usersAuthoritiesMapper.deleteByPrimaryKey(reqVo.getAuthority());
        if (affect1 == 0) {
            throw new BizException(BizExceptionEnum.AUTHORITIES_NOT_EXISTS);
        }
        AuthoritiesMenusExample authoritiesMenusExample = new AuthoritiesMenusExample();
        AuthoritiesMenusExample.Criteria criteria = authoritiesMenusExample.createCriteria();
        criteria.andAuthorityEqualTo(reqVo.getAuthority());
        int affect2 = authoritiesMenusMapper.deleteByExample(authoritiesMenusExample);
        DelAuthoritiesResVo delAuthoritiesResVo = new DelAuthoritiesResVo();
        delAuthoritiesResVo.setAffectRow(affect1 + affect2);
        return delAuthoritiesResVo;
    }

}