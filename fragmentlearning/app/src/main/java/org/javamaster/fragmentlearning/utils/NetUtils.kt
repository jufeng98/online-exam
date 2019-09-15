package org.javamaster.fragmentlearning.utils

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.interceptor.CommonInterceptor
import org.javamaster.fragmentlearning.listener.OperationListener
import java.io.File
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
        val client = getClient()
        val builder = FormBody.Builder()
        for (entry in reqMap) {
            builder.add(entry.key, entry.value)
        }
        val formBody = builder.build()
        val request = Request.Builder().url(reqUrl).post(formBody).build()
        val call = client.newCall(request)
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
        val client = getClient()
        val builder = FormBody.Builder()
        for (entry in reqMap) {
            builder.add(entry.key, entry.value)
        }
        val formBody = builder.build()
        val request = Request.Builder().url(reqUrl).post(formBody).build()
        client.newCall(request).enqueue(callback)
    }

    /**
     * json格式请求
     */
    fun postForResponse(reqUrl: String, reqObj: Any): Response {
        val client = getClient()
        val body = App.objectMapper.writeValueAsString(reqObj).toRequestBody(JSON)
        val request = Request.Builder().url(reqUrl).post(body).build()
        val call = client.newCall(request)
        return call.execute()
    }

    /**
     * 文件流请求
     */
    fun postForStream(reqUrl: String, operationListener: OperationListener<ByteArray>) {
        val url = AppConsts.BASE_URL + AppConsts.APP_CONTEXT + reqUrl
        val client = getClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(this::class.qualifiedName, "", e)
                operationListener.fail(AppConsts.ERROR_CODE, App.context.getString(AppConsts.ERROR_MSG))
            }

            override fun onResponse(call: Call, response: Response) {
                operationListener.success(response.body!!.bytes())
            }
        })
    }

    fun uploadImage(reqUrl: String, file: File, operationListener: OperationListener<String>) {
        val fileBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, fileBody)
            .build()
        val request = Request.Builder()
            .url(reqUrl)
            .post(requestBody)
            .build()
        getClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(this::class.qualifiedName, "", e)
                operationListener.fail(AppConsts.ERROR_CODE, App.context.getString(AppConsts.ERROR_MSG))
            }

            override fun onResponse(call: Call, response: Response) {
                operationListener.success(App.objectMapper.readTree(response.body!!.string()).get("data")[0].asText())
            }
        })
    }
}