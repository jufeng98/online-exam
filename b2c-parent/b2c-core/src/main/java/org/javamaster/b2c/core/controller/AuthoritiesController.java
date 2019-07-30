package org.javamaster.b2c.core.controller;

import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.AuthOrUnAuthUsersReqVo;
import org.javamaster.b2c.core.model.vo.AuthOrUnAuthUsersResVo;
import org.javamaster.b2c.core.model.vo.FindAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.FindAuthoritiesResVo;
import org.javamaster.b2c.core.model.vo.FindUsersAuthoritiesReqVo;
import org.javamaster.b2c.core.model.vo.FindUsersAuthoritiesResVo;
import org.javamaster.b2c.core.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/findAuthorities")
    public Result<FindAuthoritiesResVo> findAuthorities(@Validated @RequestBody FindAuthoritiesReqVo reqVo) {
        FindAuthoritiesResVo resVo = authoritiesService.findAuthorities(reqVo);
        Result<FindAuthoritiesResVo> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/findUsersAuthorities")
    public Result<FindUsersAuthoritiesResVo> findUsersAuthorities(@Validated @RequestBody FindUsersAuthoritiesReqVo reqVo) {
        FindUsersAuthoritiesResVo resVo = authoritiesService.findUsersAuthorities(reqVo);
        Result<FindUsersAuthoritiesResVo> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/authUsers")
    public Result<AuthOrUnAuthUsersResVo> authUsers(@Validated @RequestBody AuthOrUnAuthUsersReqVo reqVo) {
        AuthOrUnAuthUsersResVo resVo = authoritiesService.authUsers(reqVo);
        Result<AuthOrUnAuthUsersResVo> result = new Result(resVo);
        return result;
    }

    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/unAuthUsers")
    public Result<AuthOrUnAuthUsersResVo> unAuthUsers(@Validated @RequestBody AuthOrUnAuthUsersReqVo reqVo) {
        AuthOrUnAuthUsersResVo resVo = authoritiesService.unAuthUsers(reqVo);
        Result<AuthOrUnAuthUsersResVo> result = new Result(resVo);
        return result;
    }

}