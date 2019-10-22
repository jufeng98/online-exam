package org.javamaster.fragmentlearning.utils

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.interceptor.CommonInterceptor
import org.javamaster.fragmentlearning.listener.OperationListener
import java.io.File
import java.io.IOException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


/**
 * @author yudong
 * @date 2019/8/18
 */
object NetUtils {
    private val JSON = "application/json; charset=utf-8".toMediaType()

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

    fun getClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
//      包含header、body数据
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        return getUnsafeOkHttpClient().newBuilder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .addInterceptor(CommonInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }
        })
        val builder = OkHttpClient.Builder()
        val trustManager = trustAllCerts[0] as X509TrustManager
        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(null, null, null)
        val noSSLv3SocketFactory = NoSSLv3SocketFactory(sslContext.socketFactory)
        builder.sslSocketFactory(noSSLv3SocketFactory, trustManager)
        builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
        return builder.build()
    }

}