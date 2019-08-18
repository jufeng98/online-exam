package org.javamaster.fragmentlearning.interceptor

import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import java.io.IOException

/**
 * @author yudong
 * @date 2019/8/18
 */
class RequestInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        val value = preferences.getString(AppConsts.REMEMBER_ME_COOKIE_KEY, "")
        val newRequest = chain.request()
            .newBuilder()
            .addHeader("cookie", value)
            .build()
        return chain.proceed(newRequest)
    }
}

