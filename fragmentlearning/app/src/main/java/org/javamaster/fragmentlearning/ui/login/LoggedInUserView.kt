package org.javamaster.fragmentlearning.ui.login

/**
 * User details post authentication that is exposed to the UI
 * @author yudong
 * @date 2019/8/18
 */
data class LoggedInUserView(
    val username: String,
    val picUrl: String
)
