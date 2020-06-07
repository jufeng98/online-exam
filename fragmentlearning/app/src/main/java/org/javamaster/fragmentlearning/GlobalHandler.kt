package org.javamaster.fragmentlearning

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import com.google.android.gms.common.util.Base64Utils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.utils.NetUtils
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 捕获程序异常记录到SD卡并上传到服务器
 * @author yudong
 * @date 2019/8/18
 */
class GlobalHandler : Thread.UncaughtExceptionHandler {
    private lateinit var context: Context
    private lateinit var exceptionHandler: Thread.UncaughtExceptionHandler

    companion object {
        fun getInstance(): GlobalHandler {
            return GlobalHandler()
        }
    }

    fun init(context: Context) {
        this.context = context
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler()!!
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        try {
            Log.e(this.javaClass.name, "occur error", e)
            logExceptionToSdCard(e)
            Thread {
                uploadToServer(e)
            }.start()
        } catch (e: Exception) {
            Log.e(this.javaClass.name, "something error", e)
        } finally {
            exceptionHandler.uncaughtException(t, e)
        }
    }

    private fun uploadToServer(e: Throwable?) {
        val outputStream = ByteArrayOutputStream()
        val printWriter = PrintWriter(outputStream)
        val time = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.SIMPLIFIED_CHINESE).format(Date())
        printWriter.println(time)
        e?.printStackTrace(printWriter)
        dumpDeviceInfo(printWriter)
        printWriter.close()
        val bytes = outputStream.toByteArray()
        outputStream.close()
        val str = Base64Utils.encodeUrlSafe(bytes)
        val map = mapOf("fileName" to "fragmentlearning.log", "encodeBase64Str" to str)
        NetUtils.postForResponse(AppConsts.UPLOAD_EXCEPTIONS, map, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(this.javaClass.name, "upload error", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(this.javaClass.name, response.body!!.string())
            }

        })
    }

    private fun logExceptionToSdCard(e: Throwable?) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Log.w(this.javaClass.name, "sdcard unmounted,skip dump exception to sdcard")
            return
        }
        val path = context.getExternalFilesDir("crashlog")!!
        if (!path.exists()) {
            path.mkdirs()
        }
        val time = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.SIMPLIFIED_CHINESE).format(Date())
        val fileName = "$path$time-crash.log"
        val file = File(fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        val printWriter = PrintWriter(FileWriter(file))
        printWriter.println(time)
        e?.printStackTrace(printWriter)
        dumpDeviceInfo(printWriter)
        printWriter.close()
    }

    private fun dumpDeviceInfo(printWriter: PrintWriter) {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            printWriter.println("App Version: " + packageInfo.versionName + "_" + packageInfo.longVersionCode)
        }
        printWriter.println("Android OS Version: " + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT)
        printWriter.println("Manufacturer: " + Build.MANUFACTURER)
        printWriter.println("Mode: " + Build.MODEL)
        printWriter.println("CPU API: " + Arrays.toString(Build.SUPPORTED_ABIS))
    }
}