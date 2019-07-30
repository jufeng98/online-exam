package org.javamaster.b2c.core.service;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Users;
import org.javamaster.b2c.core.model.vo.ChangeUsersEnabledReqVo;
import org.javamaster.b2c.core.model.vo.CreateUsersReqVo;
import org.javamaster.b2c.core.model.vo.EditUsersReqVo;
import org.javamaster.b2c.core.model.vo.EditUsersResVo;
import org.javamaster.b2c.core.model.vo.FindUsersReqVo;
import org.javamaster.b2c.core.model.vo.UpdateUsersPasswordReqVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author yudong
 * @date 2018/12/9
 */
public interface UsersService {

    Users createUsers(CreateUsersReqVo reqVo, UserDetails userDetails);

    Users findUsersByUsername(String username);

    PageInfo<Users> findUsers(FindUsersReqVo reqVo);

    Integer changeUsersEnabled(ChangeUsersEnabledReqVo reqVo, UserDetails userDetails);


    Integer updatePassword(UpdateUsersPasswordReqVo reqVo, UserDetails userDetails);

    Integer deleteUsers(String username, UserDetails userDetails);

    EditUsersResVo editUsers(EditUsersReqVo reqVo, UserDetails userDetails);
}
