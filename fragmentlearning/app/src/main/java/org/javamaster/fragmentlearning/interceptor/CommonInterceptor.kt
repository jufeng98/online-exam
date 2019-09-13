package org.javamaster.fragmentlearning.interceptor

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Interceptor
import okhttp3.Response
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.ActionConsts
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.exception.LoginException
import org.javamaster.fragmentlearning.service.LoginService.Companion.REMEMBER_ME_COOKIE_KEY
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

/**
 * @author yudong
 * @date 2019/8/18
 */
class CommonInterceptor : Interceptor {
    private val objectMapper = ObjectMapper()
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val preferences = App.getLoginSharedPreferences()
        val value = preferences.getString(REMEMBER_ME_COOKIE_KEY, "")
        val request = chain.request()
            .newBuilder()
            .addHeader("cookie", value ?: "")
            .build()
        val response = chain.proceed(request)
        when (response.code) {
            301, 302, 304, 401, 403 -> {
                val localBroadcastManager = LocalBroadcastManager.getInstance(App.context)
                val intent = Intent(ActionConsts.FORCE_OFFLINE)
                localBroadcastManager.sendBroadcast(intent)
                throw LoginException(AppConsts.LOGIN_ERROR_CODE, App.context.getString(R.string.login_invalided))
            }
            in 500..599 -> {
                throw IOException(App.context.getString(AppConsts.ERROR_MSG))
            }
        }
        val responseBody = response.body!!
        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer
        val contentType = responseBody.contentType()
        if (contentType != null) {
            if (!contentType.subtype.contains("json")) {
                return response
            }
        }
        val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
        val resJsonStr = buffer.clone().readString(charset)
        val jsonNode = objectMapper.readTree(resJsonStr)
        if (!jsonNode.get("success").asBoolean()) {
            val errorCode = jsonNode.get("errorCode").asInt(-1)
            if (1007 == errorCode) {
                val localBroadcastManager = LocalBroadcastManager.getInstance(App.context)
                val intent = Intent(ActionConsts.FORCE_OFFLINE)
                localBroadcastManager.sendBroadcast(intent)
                throw LoginException(AppConsts.LOGIN_ERROR_CODE, App.context.getString(R.string.login_invalided))
            }
        }
        return response
    }
}

