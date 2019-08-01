package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.GetAuthoritiesMenusListReqVo;
import org.javamaster.b2c.core.model.vo.GetUsersMenusListReqVo;
import org.javamaster.b2c.core.model.vo.MenusListResVo;
import org.javamaster.b2c.core.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/getUsersMenusList")
    public Result<MenusListResVo> getUsersMenusList(@Validated @RequestBody GetUsersMenusListReqVo reqVo,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        MenusListResVo resVo = menusService.getMenusList(reqVo, userDetails);
        Result<MenusListResVo> result = new Result(resVo);
        return result;
    }

    @PostMapping("/getMenusList")
    public Result<MenusListResVo> getMenusList(@Validated @RequestBody GetUsersMenusListReqVo reqVo) {
        MenusListResVo resVo = menusService.getMenusList(reqVo, null);
        Result<MenusListResVo> result = new Result(resVo);
        return result;
    }

    @PostMapping("/getAuthoritiesMenusList")
    public Result<MenusListResVo> getAuthoritiesMenusList(@Validated @RequestBody GetAuthoritiesMenusListReqVo reqVo) {
        MenusListResVo resVo = menusService.getAuthoritiesMenusList(reqVo);
        Result<MenusListResVo> result = new Result(resVo);
        return result;
    }

}