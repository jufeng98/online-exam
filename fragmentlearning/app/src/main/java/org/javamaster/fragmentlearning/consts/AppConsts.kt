package org.javamaster.fragmentlearning.consts

import org.javamaster.fragmentlearning.BuildConfig

/**
 * @author yudong
 * @date 2019/8/18
 */
object AppConsts {
    const val BASE_URL = BuildConfig.BASE_URL
    const val APP_CONTEXT = "/onlineExam"
    const val LOGIN_URL = "$BASE_URL$APP_CONTEXT/core/login"
    const val UPLOAD_EXCEPTIONS = "$BASE_URL$APP_CONTEXT/core/log/uploadExceptions"
    const val ERROR_MSG = "网络繁忙,请稍后再试"
    const val REMEMBER_ME_COOKIE_KEY = "rememberMeKey"
    const val REGISTER_FLAG = "registerFlag"
}