package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.model.vo.GetAuthoritiesMenusListReqVo;
import org.javamaster.b2c.core.model.vo.GetUsersMenusListReqVo;
import org.javamaster.b2c.core.model.vo.MenusListResVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 系统菜单管理
 *
 * @author yudong
 * @date 2019/7/22
 */
public interface MenusService {

    /**
     * 获取菜单列表
     *
     * @param reqVo
     * @param userDetails
     * @return
     */
    MenusListResVo getMenusList(GetUsersMenusListReqVo reqVo, UserDetails userDetails);

    /**
     * 获取角色菜单信息
     *
     * @param reqVo
     * @return
     */
    MenusListResVo getAuthoritiesMenusList(GetAuthoritiesMenusListReqVo reqVo);

}