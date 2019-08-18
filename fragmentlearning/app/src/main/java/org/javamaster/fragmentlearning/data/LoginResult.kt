package org.javamaster.fragmentlearning.data

import org.javamaster.fragmentlearning.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 * @author yudong
 * @date 2019/8/18
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: String? = null
)
