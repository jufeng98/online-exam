package org.javamaster.b2c.core.service;

import org.javamaster.b2c.core.model.vo.GetMenusListReqVo;
import org.javamaster.b2c.core.model.vo.GetMenusListResVo;

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
     * @return
     */
    GetMenusListResVo getMenusList(GetMenusListReqVo reqVo);

}