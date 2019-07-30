package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.model.vo.AuthOrUnAuthUsersReqVo;
import org.javamaster.b2c.core.model.vo.AuthOrUnAuthUsersResVo;
import org.javamaster.b2c.core.model.vo.FindAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.FindAuthoritiesResVo;
import org.javamaster.b2c.core.model.vo.FindUsersAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.FindUsersAuthoritiesResVo;

/**
 * 权限管理
 *
 * @author yudong
 * @date 2019/7/28
 */
public interface AuthoritiesService {

    /**
     * 获取用户权限信息
     *
     * @param reqVo
     * @return
     */
    FindAuthoritiesResVo findAuthorities(FindAuthoritiesReqVo reqVo);

    /**
     * 获取权限信息
     *
     * @param reqVo
     * @return
     */
    FindUsersAuthoritiesResVo findUsersAuthorities(FindUsersAuthoritiesReqVo reqVo);

    /**
     * 授权用户
     *
     * @param reqVo
     * @return
     */
    AuthOrUnAuthUsersResVo authUsers(AuthOrUnAuthUsersReqVo reqVo);

    /**
     * 取消用户授权
     *
     * @param reqVo
     * @return
     */
    AuthOrUnAuthUsersResVo unAuthUsers(AuthOrUnAuthUsersReqVo reqVo);

}