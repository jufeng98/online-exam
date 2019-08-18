package org.javamaster.fragmentlearning

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
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
    private var context: Context? = null
    private lateinit var exceptionHandler: Thread.UncaughtExceptionHandler

    companion object {
        fun getInstance(): GlobalHandler {
            return GlobalHandler()
        }
    }

    fun init(context: Context?) {
        this.context = context
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        try {
            logExceptionToSdCard(e)
            uploadToServer()
            if (exceptionHandler != null) {
                exceptionHandler.uncaughtException(t, e)
            } else {
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(-1)
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.name, t.toString(), e)
        }
    }

    private fun uploadToServer() {
        // TODO
    }

    private fun logExceptionToSdCard(e: Throwable?) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Log.w(GlobalHandler.javaClass.name, "sdcard unmounted,skip dump exception to sdcard")
            return
        }
        var path = Environment.getExternalStorageDirectory().path + "/fragmentlearning/crashlog/"
        var dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        var time = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
        var fileName = "$path$time-crash.log"
        var file = File(fileName)
        val printWriter = PrintWriter(FileWriter(file))
        printWriter.println(time)
        e?.printStackTrace(printWriter)
        dumpDeviceInfo(printWriter)
        printWriter.close()
    }

    private fun dumpDeviceInfo(printWriter: PrintWriter) {
        var packageManager = context!!.packageManager
        var packageInfo = packageManager.getPackageInfo(context!!.packageName, PackageManager.GET_ACTIVITIES)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            printWriter.println("App Version: " + packageInfo.versionName + "_" + packageInfo.longVersionCode)
        }
        printWriter.println("Android OS Version: " + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT)
        printWriter.println("Vender: " + Build.MANUFACTURER)
        printWriter.println("Mode: " + Build.MODEL)
        printWriter.println("CPU API: " + Arrays.toString(Build.SUPPORTED_ABIS))
    }
}