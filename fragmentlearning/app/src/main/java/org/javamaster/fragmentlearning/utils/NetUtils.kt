package org.javamaster.fragmentlearning.utils

import okhttp3.OkHttpClient
import org.javamaster.fragmentlearning.interceptor.RequestInterceptor
import java.util.concurrent.TimeUnit

/**
 * @author yudong
 * @date 2019/8/18
 */
object NetUtils {
    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor())
            .build()
    }
}