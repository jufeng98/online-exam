package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.GetMenusListReqVo;
import org.javamaster.b2c.core.model.vo.GetMenusListResVo;
import org.javamaster.b2c.core.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统菜单管理
 *
 * @author yudong
 * @date 2019/7/22
 */
@RestController
@RequestMapping("/core/menus")
public class MenusController {

    @Autowired
    private MenusService menusService;

    @PostMapping("/getMenusList")
    public Result<GetMenusListResVo> getMenusList(@Validated @RequestBody GetMenusListReqVo reqVo) {
        GetMenusListResVo resVo = menusService.getMenusList(reqVo);
        Result<GetMenusListResVo> result = new Result(resVo);
        return result;
    }

}