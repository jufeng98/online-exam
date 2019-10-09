package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.UsersAuthorities;
import org.javamaster.b2c.core.model.Result;
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
import org.javamaster.b2c.core.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限管理
 *
 * @author yudong
 * @date 2019/7/28
 */
@RestController
@RequestMapping("/admin/authorities")
public class AuthoritiesController {

    @Autowired
    private AuthoritiesService authoritiesService;

    @PostMapping("/findAuthorities")
    public Result<FindAuthoritiesResVo> findAuthorities(@Validated @RequestBody FindAuthoritiesReqVo reqVo) {
        FindAuthoritiesResVo resVo = authoritiesService.findAuthorities(reqVo);
        return new Result<>(resVo);
    }

    @PostMapping("/findUsersAuthorities")
    public Result<List<UsersAuthorities>> findUsersAuthorities(@Validated @RequestBody FindUsersAuthoritiesReqVo reqVo) {
        PageInfo<UsersAuthorities> pageInfo = authoritiesService.findUsersAuthorities(reqVo);
        return new Result<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @PostMapping("/authUsers")
    public Result<AuthOrUnAuthUsersResVo> authUsers(@Validated @RequestBody AuthOrUnAuthUsersReqVo reqVo) {
        AuthOrUnAuthUsersResVo resVo = authoritiesService.authUsers(reqVo);
        return new Result<>(resVo);
    }

    @PostMapping("/unAuthUsers")
    public Result<AuthOrUnAuthUsersResVo> unAuthUsers(@Validated @RequestBody AuthOrUnAuthUsersReqVo reqVo) {
        AuthOrUnAuthUsersResVo resVo = authoritiesService.unAuthUsers(reqVo);
        return new Result<>(resVo);
    }

    @PostMapping("/createAuthorities")
    public Result<UsersAuthorities> createAuthorities(@Validated @RequestBody CreateAuthoritiesReqVo reqVo) {
        UsersAuthorities resVo = authoritiesService.createAuthorities(reqVo);
        return new Result<>(resVo);
    }

    @PostMapping("/changeAuthoritiesMenus")
    public Result<ChangeAuthoritiesMenusResVo> changeAuthoritiesMenus(@Validated @RequestBody ChangeAuthoritiesMenusReqVo reqVo) {
        ChangeAuthoritiesMenusResVo resVo = authoritiesService.changeAuthoritiesMenus(reqVo);
        return new Result<>(resVo);
    }

    @PostMapping("/editAuthorities")
    public Result<UsersAuthorities> editAuthorities(@Validated @RequestBody EditAuthoritiesReqVo reqVo) {
        UsersAuthorities resVo = authoritiesService.editAuthorities(reqVo);
        return new Result<>(resVo);
    }

    @PostMapping("/delAuthorities")
    public Result<DelAuthoritiesResVo> delAuthorities(@Validated @RequestBody DelAuthoritiesReqVo reqVo) {
        DelAuthoritiesResVo resVo = authoritiesService.delAuthorities(reqVo);
        return new Result<>(resVo);
    }

}