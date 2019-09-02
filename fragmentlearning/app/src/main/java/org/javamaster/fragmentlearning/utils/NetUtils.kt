package org.javamaster.fragmentlearning.utils

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .addInterceptor(CommonInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun postForResponse(reqUrl: String, reqMap: Map<String, String>): Response {
        var client = getClient()
        var builder = FormBody.Builder()
        for (entry in reqMap) {
            builder.add(entry.key, entry.value)
        }
        var formBody = builder.build()
        var request = Request.Builder().url(reqUrl).post(formBody).build()
        var call = client.newCall(request)
        return call.execute()
    }
}