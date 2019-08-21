package org.javamaster.fragmentlearning.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.javamaster.fragmentlearning.interceptor.CommonInterceptor
import java.util.concurrent.TimeUnit


/**
 * @author yudong
 * @date 2019/8/18
 */
object NetUtils {
    fun getClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
//      包含header、body数据
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(CommonInterceptor())
            .build()
    }
}