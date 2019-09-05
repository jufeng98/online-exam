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
import org.javamaster.fragmentlearning.data.LoginService.Companion.LOGIN_USER_INFO
import org.javamaster.fragmentlearning.data.LoginService.Companion.REGISTER_FLAG
import org.javamaster.fragmentlearning.data.LoginService.Companion.REMEMBER_ME_COOKIE_KEY
import org.javamaster.fragmentlearning.data.model.CreateUsersReqVo
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.utils.NetUtils

/**
 * @author yudong
 * @date 2019/8/18
 */
class LoginServiceImpl constructor(private val objectMapper: ObjectMapper) : LoginService {

    override fun login(username: String, password: String): ResultVo<User> {
        var map = mapOf("username" to username, "password" to password, "coreRememberMe" to "true")
        var response: Response
        try {
            response = NetUtils.postForResponse(AppConsts.LOGIN_URL, map)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, map.toString(), e)
            return ResultVo(AppConsts.ERROR_CODE, App.context.getString(AppConsts.ERROR_MSG))
        }
        var resJsonStr: String = response.body!!.string()
        var resultVo: ResultVo<User> = objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<User>>() {})
        if (!resultVo.success) {
            return resultVo
        }
        var preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        var cookieStr: String = response.headers("Set-Cookie").joinToString(";")
        preferences.putStringAndCommit(
            REMEMBER_ME_COOKIE_KEY,
            cookieStr
        )
        var userInfoJsonStr = App.objectMapper.writeValueAsString(resultVo.data)
        preferences.putStringAndCommit(LOGIN_USER_INFO, userInfoJsonStr)
        return resultVo
    }

    override fun signUp(createUsersReqVo: CreateUsersReqVo): ResultVo<User> {
        var response: Response
        try {
            response = NetUtils.postForResponse(AppConsts.SIGN_UP_URL, createUsersReqVo)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, createUsersReqVo.toString(), e)
            return ResultVo(AppConsts.ERROR_CODE, App.context.getString(AppConsts.ERROR_MSG))
        }
        var resJsonStr: String = response.body!!.string()
        var resultVo: ResultVo<User> = objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<User>>() {})
        if (!resultVo.success) {
            return resultVo
        }
        var preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        var edit = preferences.edit()
        edit.putBoolean(REGISTER_FLAG, true)
        edit.commit()
        return login(
            createUsersReqVo.createOrEditUsersForm!!.username!!,
            createUsersReqVo.createOrEditUsersForm!!.password!!
        )
    }

    private fun SharedPreferences.putStringAndCommit(key: String, value: String) {
        var editor = this.edit()
        editor.putString(key, value)
        editor.commit()
    }
}


