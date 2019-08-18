package org.javamaster.fragmentlearning.data.impl

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.FormBody
import okhttp3.Request
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
        var client = NetUtils.getClient()
        var formBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .add("coreRememberMe", "true")
            .build()
        var request = Request.Builder().url(AppConsts.LOGIN_URL).post(formBody).build()
        var call = client.newCall(request)
        var response = call.execute()
        var preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        var cookieStr: String = response.headers("Set-Cookie").joinToString(";")
        Log.i(this.javaClass.name, cookieStr)
        preferences.putStringAndCommit(
            AppConsts.REMEMBER_ME_COOKIE_KEY,
            cookieStr
        )
        var resJsonStr: String = response.body!!.string()
        Log.i(this.javaClass.name, resJsonStr)
        return objectMapper.readValue(resJsonStr, object : TypeReference<Result<User>>() {})
    }

    fun SharedPreferences.putStringAndCommit(key: String, value: String) {
        var editor = this.edit()
        editor.putString(key, value)
        editor.commit()
    }
}


