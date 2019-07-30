package org.javamaster.b2c.core.service.impl;

import org.javamaster.b2c.core.entity.Authorities;
import org.javamaster.b2c.core.entity.AuthoritiesExample;
import org.javamaster.b2c.core.entity.UsersAuthoritiesExample;
import org.javamaster.b2c.core.mapper.AuthoritiesMapper;
import org.javamaster.b2c.core.mapper.UsersAuthoritiesMapper;
import org.javamaster.b2c.core.model.vo.AuthOrUnAuthUsersReqVo;
import org.javamaster.b2c.core.model.vo.AuthOrUnAuthUsersResVo;
import org.javamaster.b2c.core.model.vo.FindAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.FindAuthoritiesResVo;
import org.javamaster.b2c.core.model.vo.FindUsersAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.FindUsersAuthoritiesResVo;
import org.javamaster.b2c.core.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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

    @Override
    public FindAuthoritiesResVo findAuthorities(FindAuthoritiesReqVo reqVo) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(reqVo.getUsername());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        FindAuthoritiesResVo findAuthoritiesResVo = new FindAuthoritiesResVo();
        findAuthoritiesResVo.setAuthorities(authorities);
        return findAuthoritiesResVo;
    }

    @Override
    public FindUsersAuthoritiesResVo findUsersAuthorities(FindUsersAuthoritiesReqVo reqVo) {
        FindUsersAuthoritiesResVo findAuthoritiesResVo = new FindUsersAuthoritiesResVo();
        findAuthoritiesResVo.setUsersAuthorities(usersAuthoritiesMapper.selectByExample(new UsersAuthoritiesExample()));
        return findAuthoritiesResVo;
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

}