package org.javamaster.fragmentlearning.service

import org.javamaster.fragmentlearning.data.model.CreateUsersReqVo
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.User

/**
 * @author yudong
 * @date 2019/8/18
 */
interface LoginService {
    companion object {
        const val REMEMBER_ME_COOKIE_KEY = "rememberMeKey"
        const val LOGIN_USER_INFO = "loginUserInfo"
    }

    fun login(username: String, password: String): ResultVo<User>
    fun logout()
    fun signUp(createUsersReqVo: CreateUsersReqVo): ResultVo<User>
    fun editUsers(userInfo: User): ResultVo<Int>
    fun changePwd(username: String, password: String, newPassword: String): ResultVo<Int>
}