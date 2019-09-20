package org.javamaster.fragmentlearning.service.impl

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
import org.javamaster.fragmentlearning.data.model.CreateUsersReqVo
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.exception.LoginException
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LoginService
import org.javamaster.fragmentlearning.service.LoginService.Companion.LOGIN_USER_INFO
import org.javamaster.fragmentlearning.service.LoginService.Companion.USERNAME
import org.javamaster.fragmentlearning.utils.ImageUtils
import org.javamaster.fragmentlearning.utils.NetUtils
import java.io.IOException

/**
 * @author yudong
 * @date 2019/8/18
 */
class LoginServiceImpl constructor(private val objectMapper: ObjectMapper) : LoginService {

    override fun login(username: String, password: String): ResultVo<User> {
        val map = mapOf("username" to username, "password" to password, "coreRememberMe" to "true")
        val response: Response
        try {
            response = NetUtils.postForResponse(AppConsts.LOGIN_URL, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<User> = objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<User>>() {})
        if (!resultVo.success) {
            return resultVo
        }
        val preferences = App.getLoginSharedPreferences()
        val userInfoJsonStr = App.objectMapper.writeValueAsString(resultVo.data!!)
        preferences.putStringAndCommit(LOGIN_USER_INFO, userInfoJsonStr)
        preferences.putStringAndCommit(USERNAME, resultVo.data.username)
        val picUrl = resultVo.data.picUrl
        if (picUrl != "") {
            val suffix = picUrl.substring(picUrl.lastIndexOf("."))
            NetUtils.postForStream(picUrl, object : OperationListener<ByteArray> {
                override fun success(t: ByteArray) {
                    ImageUtils.saveUserPhoto(suffix, t)
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
        val preferences = App.getLoginSharedPreferences()
        preferences.edit()
            .apply {
                clear()
                apply()
            }
    }

    override fun signUp(createUsersReqVo: CreateUsersReqVo): ResultVo<User> {
        val response: Response
        try {
            response = NetUtils.postForResponse(AppConsts.SIGN_UP_URL, createUsersReqVo)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<User> = objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<User>>() {})
        if (!resultVo.success) {
            return resultVo
        }
        return login(
            createUsersReqVo.createOrEditUsersForm!!.username!!,
            createUsersReqVo.createOrEditUsersForm!!.password!!
        )
    }

    override fun editUsers(userInfo: User): ResultVo<Int> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["createOrEditUsersForm"] = userInfo
            response = NetUtils.postForResponse(AppConsts.EDIT_URL, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val userInfoJsonStr = App.objectMapper.writeValueAsString(userInfo)
        val preferences = App.getLoginSharedPreferences()
        preferences.putStringAndCommit(LOGIN_USER_INFO, userInfoJsonStr)
        val resJsonStr: String = response.body!!.string()
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<Int>>() {})
    }

    override fun changePwd(username: String, password: String, newPassword: String): ResultVo<Int> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["username"] = username
            map["password"] = password
            map["newPassword"] = newPassword
            response = NetUtils.postForResponse(AppConsts.UPDATE_USERS_PASSWORD, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<Int>>() {})
    }

    private fun SharedPreferences.putStringAndCommit(key: String, value: String) {
        this.edit().apply {
            putString(key, value)
            apply()
        }
    }

}


