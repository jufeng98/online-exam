package org.javamaster.fragmentlearning

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import com.google.android.gms.common.util.Base64Utils
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.utils.NetUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
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
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        try {
            Log.e(GlobalHandler.javaClass.name, "occur error", e)
            logExceptionToSdCard(e)
            Thread {
                uploadToServer(e)
            }.start()
        } catch (e: Exception) {
            Log.e(this.javaClass.name, t.toString(), e)
        } finally {
            if (exceptionHandler != null) {
                exceptionHandler.uncaughtException(t, e)
            } else {
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(-1)
            }
        }
    }

    private fun uploadToServer(e: Throwable?) {
        var outputStream = ByteArrayOutputStream()
        val printWriter = PrintWriter(outputStream)
        var time = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
        printWriter.println(time)
        e?.printStackTrace(printWriter)
        dumpDeviceInfo(printWriter)
        printWriter.close()
        var bytes = outputStream.toByteArray()
        outputStream.close()
        var str = Base64Utils.encodeUrlSafe(bytes)
        var map = mapOf("fileName" to "fragmentlearning.log", "encodeBase64Str" to str)
        NetUtils.postForResponse(AppConsts.UPLOAD_EXCEPTIONS, map)
    }

    private fun logExceptionToSdCard(e: Throwable?) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Log.w(GlobalHandler.javaClass.name, "sdcard unmounted,skip dump exception to sdcard")
            return
        }
        var path = context.getExternalFilesDir("").path.replace(".", "/") + "/fragmentlearning/crashlog/"
        var dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        var time = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
        var fileName = "$path$time-crash.log"
        var file = File(fileName)
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
        var packageManager = context.packageManager
        var packageInfo = packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            printWriter.println("App Version: " + packageInfo.versionName + "_" + packageInfo.longVersionCode)
        }
        printWriter.println("Android OS Version: " + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT)
        printWriter.println("Manufacturer: " + Build.MANUFACTURER)
        printWriter.println("Mode: " + Build.MODEL)
        printWriter.println("CPU API: " + Arrays.toString(Build.SUPPORTED_ABIS))
    }
}