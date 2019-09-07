package org.javamaster.fragmentlearning.utils

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.interceptor.CommonInterceptor
import org.javamaster.fragmentlearning.listener.OperationListener
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * @author yudong
 * @date 2019/8/18
 */
object NetUtils {
    private val JSON = "application/json; charset=utf-8".toMediaType()
    private fun getClient(): OkHttpClient {
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

    /**
     * form表单格式请求
     */
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

    /**
     * form表单格式请求
     */
    fun postForResponse(
        reqUrl: String,
        reqMap: Map<String, String>,
        callback: Callback
    ) {
        var client = getClient()
        var builder = FormBody.Builder()
        for (entry in reqMap) {
            builder.add(entry.key, entry.value)
        }
        var formBody = builder.build()
        var request = Request.Builder().url(reqUrl).post(formBody).build()
        client.newCall(request).enqueue(callback)
    }

    /**
     * json格式请求
     */
    fun postForResponse(reqUrl: String, reqObj: Any): Response {
        var client = getClient()
        val body = App.objectMapper.writeValueAsString(reqObj).toRequestBody(JSON)
        var request = Request.Builder().url(reqUrl).post(body).build()
        var call = client.newCall(request)
        return call.execute()
    }

    /**
     * 文件流请求
     */
    fun postForStream(reqUrl: String, operationListener: OperationListener<ByteArray>) {
        var url = AppConsts.BASE_URL + AppConsts.APP_CONTEXT + reqUrl
        var client = getClient()
        var request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                operationListener.fail(e)
            }

            override fun onResponse(call: Call, response: Response) {
                operationListener.success(response.body!!.bytes())
            }
        })

    }
}