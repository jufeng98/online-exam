package org.javamaster.fragmentlearning.data.impl

import android.content.SharedPreferences
import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.LoginService
import org.javamaster.fragmentlearning.data.LoginService.Companion.LOGIN_USER_INFO
import org.javamaster.fragmentlearning.data.LoginService.Companion.REMEMBER_ME_COOKIE_KEY
import org.javamaster.fragmentlearning.data.model.CreateUsersReqVo
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.utils.ImageUtils
import org.javamaster.fragmentlearning.utils.NetUtils
import java.io.IOException

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
            return ResultVo(errorMsg = e.message ?: App.context.getString(R.string.network_error))
        }
        var resJsonStr: String = response.body!!.string()
        var resultVo: ResultVo<User> = objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<User>>() {})
        if (!resultVo.success) {
            return resultVo
        }

        var preferences = App.getLoginSharedPreferences()
        var cookieStr: String = response.headers("Set-Cookie").joinToString(";")
        preferences.putStringAndCommit(
            REMEMBER_ME_COOKIE_KEY,
            cookieStr
        )
        var userInfoJsonStr = App.objectMapper.writeValueAsString(resultVo.data)
        preferences.putStringAndCommit(LOGIN_USER_INFO, userInfoJsonStr)
        var picUrl = resultVo.data!!.picUrl
        if (picUrl != "") {
            var suffix = picUrl.substring(picUrl.lastIndexOf("."))
            NetUtils.postForStream(picUrl, object : OperationListener<ByteArray> {
                override fun success(t: ByteArray) {
                    ImageUtils.saveUserPhoto(suffix, t)
                }

                override fun fail(e: Exception) {
                    Log.e(this::class.qualifiedName, "get img error", e)
                }
            })
        }

        return resultVo
    }

    override fun logout() {
        NetUtils.postForResponse(AppConsts.LOGOUT_URL, mapOf(), object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(this::class.qualifiedName, "error", e)
                clearLoginInfo()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(this::class.qualifiedName, response.body?.string())
                clearLoginInfo()
            }
        })
    }

    private fun clearLoginInfo() {
        var preferences = App.getLoginSharedPreferences()
        var edit = preferences.edit()
        edit.clear()
        edit.commit()
    }

    override fun signUp(createUsersReqVo: CreateUsersReqVo): ResultVo<User> {
        var response: Response
        try {
            response = NetUtils.postForResponse(AppConsts.SIGN_UP_URL, createUsersReqVo)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, createUsersReqVo.toString(), e)
            return ResultVo(errorMsg = e.message ?: App.context.getString(R.string.network_error))
        }
        var resJsonStr: String = response.body!!.string()
        var resultVo: ResultVo<User> = objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<User>>() {})
        if (!resultVo.success) {
            return resultVo
        }
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


