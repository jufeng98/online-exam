package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.consts.AppConsts;
import org.javamaster.b2c.core.entity.Users;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.*;
import org.javamaster.b2c.core.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 管理用户信息
 *
 * @author yudong
 * @date 2019/7/5
 */
@Validated
@RestController
@RequestMapping("/admin/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * 创建用户
     */
    @PostMapping("/createUsers")
    public Result<Users> createUsers(@Validated @RequestBody CreateUsersReqVo reqVo,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return new Result<>(usersService.createUsers(reqVo, userDetails));
    }

    /**
     * 启用或者禁用用户
     */
    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/changeUsersEnabled")
    public Result<Integer> changeUsersEnabled(@Validated @RequestBody ChangeUsersEnabledReqVo reqVo, @AuthenticationPrincipal UserDetails userDetails) {
        return new Result<>(usersService.changeUsersEnabled(reqVo, userDetails));
    }

    /**
     * 拥有管理员权限可查看任何用户信息,否则只能查看自己的信息
     */
    @PreAuthorize("hasAuthority(T(org.javamaster.b2c.core.consts.AppConsts).ROLE_ADMIN) " +
            "or #reqVo.usersForm.username == #userDetails.username")
    @PostMapping("/findUsers")
    public Result<List<Users>> findUsers(@Validated @RequestBody FindUsersReqVo reqVo,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        PageInfo<Users> pageInfo = usersService.findUsers(reqVo);
        return new Result<>(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 拥有管理员权限可修改任何用户的密码,否则只能修改自己的密码
     */
    @PreAuthorize("hasAuthority(T(org.javamaster.b2c.core.consts.AppConsts).ROLE_ADMIN) " +
            "or (#reqVo.username == #userDetails.username and T(org.apache.commons.lang3.StringUtils).isNotBlank(#reqVo.password))")
    @PostMapping("/updateUsersPassword")
    public Result<Integer> updatePassword(@Validated @RequestBody UpdateUsersPasswordReqVo reqVo,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        return new Result<>(usersService.updatePassword(reqVo, userDetails));
    }

    /**
     * 删除用户(逻辑删除)
     */
    @Secured(AppConsts.ROLE_ADMIN)
    @PostMapping("/deleteUsers")
    public Result<Integer> deleteUsers(@NotBlank String username, @AuthenticationPrincipal UserDetails userDetails) {
        return new Result<>(usersService.deleteUsers(username, userDetails));
    }

    @PreAuthorize("hasAuthority(T(org.javamaster.b2c.core.consts.AppConsts).ROLE_ADMIN) " +
            "or (#reqVo.createOrEditUsersForm.username == #userDetails.username)")
    @PostMapping("/editUsers")
    public Result<Integer> editUsers(@Validated @RequestBody EditUsersReqVo reqVo, @AuthenticationPrincipal UserDetails userDetails) {
        Integer resVo = usersService.editUsers(reqVo, userDetails);
        return new Result<>(resVo);
    }

}