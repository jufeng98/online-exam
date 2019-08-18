package org.javamaster.fragmentlearning.data

import org.javamaster.fragmentlearning.data.model.Result
import org.javamaster.fragmentlearning.data.model.User

/**
 * @author yudong
 * @date 2019/8/18
 */
interface LoginService {
    fun login(username: String, password: String): Result<User>
}