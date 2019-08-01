package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.UsersAuthorities;
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
    PageInfo<UsersAuthorities> findUsersAuthorities(FindUsersAuthoritiesReqVo reqVo);

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

    /**
     * 创建角色
     *
     * @param reqVo
     * @return
     */
    UsersAuthorities createAuthorities(CreateAuthoritiesReqVo reqVo);

    /**
     * 修改角色的菜单信息
     *
     * @param reqVo
     * @return
     */
    ChangeAuthoritiesMenusResVo changeAuthoritiesMenus(ChangeAuthoritiesMenusReqVo reqVo);

    /**
     * 编辑角色
     *
     * @param reqVo
     * @return
     */
    UsersAuthorities editAuthorities(EditAuthoritiesReqVo reqVo);

    /**
     * 删除角色
     *
     * @param reqVo
     * @return
     */
    DelAuthoritiesResVo delAuthorities(DelAuthoritiesReqVo reqVo);

}