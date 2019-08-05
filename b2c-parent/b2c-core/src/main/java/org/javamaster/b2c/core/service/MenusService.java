package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.model.vo.CreateMenusReqVo;
import org.javamaster.b2c.core.model.vo.CreateMenusResVo;
import org.javamaster.b2c.core.model.vo.DelMenusReqVo;
import org.javamaster.b2c.core.model.vo.DelMenusResVo;
import org.javamaster.b2c.core.model.vo.EditMenusReqVo;
import org.javamaster.b2c.core.model.vo.EditMenusResVo;
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

    /**
     * 创建菜单
     *
     * @param reqVo
     * @return
     */
    CreateMenusResVo createMenus(CreateMenusReqVo reqVo);

    /**
     * 删除菜单
     *
     * @param reqVo
     * @return
     */
    DelMenusResVo delMenus(DelMenusReqVo reqVo);

    /**
     * 编辑菜单
     *
     * @param reqVo
     * @return
     */
    EditMenusResVo editMenus(EditMenusReqVo reqVo);

}