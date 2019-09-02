package org.javamaster.fragmentlearning.data.impl

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Response
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.LoginService
import org.javamaster.fragmentlearning.data.model.Result
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.utils.NetUtils

/**
 * @author yudong
 * @date 2019/8/18
 */
class LoginServiceImpl constructor(private val objectMapper: ObjectMapper) : LoginService {

    override fun login(username: String, password: String): Result<User> {
        var map = mapOf("username" to username, "password" to password, "coreRememberMe" to "true")
        var response: Response
        try {
            response = NetUtils.postForResponse(AppConsts.LOGIN_URL, map)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, map.toString(), e)
            return Result(-1, AppConsts.ERROR_MSG)
        }
        var resJsonStr: String = response.body!!.string()
        var result: Result<User> = objectMapper.readValue(resJsonStr, object : TypeReference<Result<User>>() {})
        if (result.success) {
            var preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
            var cookieStr: String = response.headers("Set-Cookie").joinToString(";")
            preferences.putStringAndCommit(
                AppConsts.REMEMBER_ME_COOKIE_KEY,
                cookieStr
            )
        }
        return result
    }

    private fun SharedPreferences.putStringAndCommit(key: String, value: String) {
        var editor = this.edit()
        editor.putString(key, value)
        editor.commit()
    }
}


