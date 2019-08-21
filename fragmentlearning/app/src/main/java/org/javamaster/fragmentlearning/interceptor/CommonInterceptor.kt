package org.javamaster.fragmentlearning.interceptor

import android.preference.PreferenceManager
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Interceptor
import okhttp3.Response
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
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
        var preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        val value = preferences.getString(AppConsts.REMEMBER_ME_COOKIE_KEY, "")
        val request = chain.request()
            .newBuilder()
            .addHeader("cookie", value)
            .build()
        val response = chain.proceed(request)
        when (response.code) {
            302, 401, 403 -> {
//          TODO 登录失效
            }
        }
        val responseBody = response.body!!
        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        var buffer = source.buffer
        val contentType = responseBody.contentType()
        val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
        val resJsonStr = buffer.clone().readString(charset)
        val jsonNode = objectMapper.readTree(resJsonStr)
        if (!jsonNode.get("success").asBoolean()) {
            val errorCode = jsonNode.get("errorCode").asInt(-1)
            if (1007 == errorCode) {
//                TODO 登录失效
            }
        }
        return response
    }
}

