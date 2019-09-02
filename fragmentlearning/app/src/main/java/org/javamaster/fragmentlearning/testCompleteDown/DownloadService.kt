package org.javamaster.fragmentlearning.testCompleteDown

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.testActivity.SaveAndLoadActivity

class DownloadService : Service() {

    lateinit var fileName: String

    lateinit var downloadTask: DownloadTask
    var downloadListener: DownloadListener = object : DownloadListener {
        override fun onProgress(progress: Int) {
            getNotificationManager().notify(1, getNotification("正在下载${fileName}", progress))
        }

        override fun onSuccess() {
            stopForeground(true)
            getNotificationManager().notify(1, getNotification("完成${fileName}下载", 100))
            Toast.makeText(this@DownloadService, "${fileName}下载完成", Toast.LENGTH_SHORT).show()
        }

        override fun onFailed() {
            getNotificationManager().notify(1, getNotification("${fileName}下载失败", -1))
            Toast.makeText(this@DownloadService, "${fileName}下载失败", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            getNotificationManager().notify(1, getNotification("暂停${fileName}下载", -1))
            Toast.makeText(this@DownloadService, "暂停${fileName}下载", Toast.LENGTH_SHORT).show()
        }

        override fun onCanceled() {
            stopForeground(true)
            Toast.makeText(this@DownloadService, "${fileName}取消下载", Toast.LENGTH_SHORT).show()
        }

    }
    private var downloadBinder = DownloadBinder()

    override fun onBind(intent: Intent): IBinder {
        return downloadBinder
    }

    inner class DownloadBinder : Binder() {
        fun startDownload(url: String) {
            var index = url.lastIndexOf("/")
            fileName = url.substring(index + 1)
            downloadTask = DownloadTask(downloadListener, cancel = false, pause = false)
            downloadTask.execute(url)
            startForeground(1, getNotification("下载中", 0))
        }

        fun pauseDownload() {
            downloadTask.pause = true
        }

        fun stopDownload() {
            downloadTask.cancel = true
        }
    }

    fun getNotificationManager(): NotificationManager {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun getNotification(title: String, progress: Int): Notification {
        val intent = Intent(this, SaveAndLoadActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        var builder = NotificationCompat.Builder(this)
            .setContentTitle(title)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pi)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
        if (progress >= 0) {
            builder.setContentText("$progress%")
            builder.setProgress(100, progress, false)
        }
        return builder.build()
    }
}

