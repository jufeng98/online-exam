package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.model.vo.GetMenusListReqVo;
import org.javamaster.b2c.core.model.vo.GetMenusListResVo;
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
    GetMenusListResVo getMenusList(GetMenusListReqVo reqVo, UserDetails userDetails);

}