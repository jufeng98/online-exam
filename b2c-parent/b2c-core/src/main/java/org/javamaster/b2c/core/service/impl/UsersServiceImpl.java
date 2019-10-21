package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.entity.Authorities;
import org.javamaster.b2c.core.entity.Users;
import org.javamaster.b2c.core.entity.UsersExample;
import org.javamaster.b2c.core.enums.BizExceptionEnum;
import org.javamaster.b2c.core.exception.BizException;
import org.javamaster.b2c.core.mapper.AuthoritiesMapper;
import org.javamaster.b2c.core.mapper.UsersMapper;
import org.javamaster.b2c.core.model.Page;
import org.javamaster.b2c.core.model.vo.ChangeUsersEnabledReqVo;
import org.javamaster.b2c.core.model.vo.CreateUsersReqVo;
import org.javamaster.b2c.core.model.vo.EditUsersReqVo;
import org.javamaster.b2c.core.model.vo.FindUsersReqVo;
import org.javamaster.b2c.core.model.vo.UpdateUsersPasswordReqVo;
import org.javamaster.b2c.core.service.UsersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yudong
 * @date 2019/7/5
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthoritiesMapper authoritiesMapper;

    @Override
    public Users findUsersByUsername(String username) {
        return usersMapper.selectByPrimaryKey(username);
    }

    @Override
    public PageInfo<Users> findUsers(FindUsersReqVo reqVo) {
        Page page = reqVo.getPage();
        page.setOrderBy("create_time desc");
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        UsersExample usersExample = new UsersExample();
        UsersExample.Criteria criteria = usersExample.createCriteria();
        criteria.andDelFlagEqualTo(false);
        if (StringUtils.isNotBlank(reqVo.getUsersForm().getUsername())) {
            criteria.andUsernameLike(reqVo.getUsersForm().getUsername());
        }
        if (StringUtils.isNotBlank(reqVo.getUsersForm().getMobile())) {
            criteria.andMobileLike(reqVo.getUsersForm().getMobile() + "%");
        }
        if (StringUtils.isNotBlank(reqVo.getUsersForm().getEmail())) {
            criteria.andEmailLike(reqVo.getUsersForm().getEmail() + "%");
        }
        if (reqVo.getUsersForm().getEnabled() != null) {
            criteria.andEnabledEqualTo(reqVo.getUsersForm().getEnabled());
        }
        List<Users> list = usersMapper.selectByExample(usersExample);
        return new PageInfo<>(list);
    }

    @Override
    public Integer changeUsersEnabled(ChangeUsersEnabledReqVo reqVo, UserDetails userDetails) {
        Users users = new Users();
        BeanUtils.copyProperties(reqVo, users);
        return usersMapper.updateByPrimaryKeySelective(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Users createUsers(CreateUsersReqVo reqVo, UserDetails userDetails) {
        if (findUsersByUsername(reqVo.getCreateOrEditUsersForm().getUsername()) != null) {
            throw new BizException(BizExceptionEnum.USER_EXISTS);
        }
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria().andEmailEqualTo(reqVo.getCreateOrEditUsersForm().getEmail());
        if (!usersMapper.selectByExample(usersExample).isEmpty()) {
            throw new BizException(BizExceptionEnum.EMAIL_EXISTS);
        }
        Users user = new Users();
        BeanUtils.copyProperties(reqVo.getCreateOrEditUsersForm(), user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userDetails != null) {
            user.setCreateUsername(userDetails.getUsername());
            user.setLastOpUsername(userDetails.getUsername());
        } else {
            user.setCreateUsername("");
            user.setLastOpUsername("");
        }
        usersMapper.insertSelective(user);
        Authorities authorities = new Authorities();
        authorities.setUsername(reqVo.getCreateOrEditUsersForm().getUsername());
        authorities.setAuthority(AppConsts.ROLE_ORDINARY);
        authoritiesMapper.insert(authorities);
        user = usersMapper.selectByPrimaryKey(reqVo.getCreateOrEditUsersForm().getUsername());
        return user;
    }

    @Override
    public Integer updatePassword(UpdateUsersPasswordReqVo reqVo, UserDetails userDetails) {
        Users dbUsers = findUsersByUsername(reqVo.getUsername());
        if (dbUsers == null) {
            throw new BizException(BizExceptionEnum.USERNAME_NOT_EXISTS);
        }
        boolean needRebuildContext = false;
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(AppConsts.ROLE_ADMIN))) {
            if (reqVo.getUsername().equals(userDetails.getUsername())) {
                needRebuildContext = true;
                // 管理员修改自己的密码,需要验证原密码
                verifyOriginalPassword(reqVo.getPassword(), userDetails.getUsername());
            }
        } else {
            // 非管理员修改自己的密码,需要验证原密码
            needRebuildContext = true;
            verifyOriginalPassword(reqVo.getPassword(), userDetails.getUsername());
        }
        dbUsers.setPassword(passwordEncoder.encode(reqVo.getNewPassword()));
        dbUsers.setLastOpUsername(userDetails.getUsername());
        dbUsers.setLastOpTime(new Date());
        int affectRows = usersMapper.updateByPrimaryKeySelective(dbUsers);
        if (needRebuildContext) {
            SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(userDetails));
        }
        return affectRows;
    }

    @Override
    public Integer deleteUsers(String username, UserDetails userDetails) {
        Users users = new Users();
        users.setUsername(username);
        users.setEnabled(false);
        users.setDelFlag(true);
        return usersMapper.updateByPrimaryKeySelective(users);
    }

    @Override
    public Integer editUsers(EditUsersReqVo reqVo, UserDetails userDetails) {
        Users users = new Users();
        BeanUtils.copyProperties(reqVo.getCreateOrEditUsersForm(), users);
        return usersMapper.updateByPrimaryKeySelective(users);
    }

    private void verifyOriginalPassword(String originalPassword, String username) {
        if (StringUtils.isBlank(originalPassword)) {
            throw new BizException(BizExceptionEnum.INVALID_REQ_PARAM);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(originalPassword, userDetails.getPassword())) {
            throw new BizException(BizExceptionEnum.PASSWORD_INCORRECT);
        }
    }

    private Authentication createNewAuthentication(UserDetails userDetails) {
        UserDetails user = userDetailsService.loadUserByUsername(userDetails.getUsername());
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        newAuthentication.setDetails(userDetails);

        return newAuthentication;
    }
}
