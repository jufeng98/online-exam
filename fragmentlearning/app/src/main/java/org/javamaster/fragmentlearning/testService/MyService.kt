package org.javamaster.fragmentlearning.testService

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.testActivity.SaveAndLoadActivity
import java.util.concurrent.TimeUnit

/**
 * 服务的代码默认运行在主线程
 */
class MyService : Service() {
    var mBinder = DownBinder()
    private var stopped = false
    private var progress = 0
    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.qualifiedName, "service first started")
        val intent = Intent(this, SaveAndLoadActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this)
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_menu_mini)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_menu))
            .setContentIntent(pi)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.banana
                    )
                )
            )
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(this::class.qualifiedName, "service started")
        var i = 0
        Thread {
            // 模拟耗时工作
            while (i++ < 6 && !stopped) {
                Log.i(this::class.qualifiedName, "$i.后台服务运行中......")
                TimeUnit.SECONDS.sleep(2)
            }
//            stopSelf()
            stopped = true
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopped = true
        super.onDestroy()
        Log.i(this::class.qualifiedName, "service destroyed")
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }


    fun startDownload() {
        Thread {
            while (progress < 100 && !stopped) {
                Log.i(this::class.qualifiedName, "后台服务下载中......")
                progress += 10
                TimeUnit.SECONDS.sleep(2)
            }
        }.start()
    }

    fun getProgress(): Int {
        return progress
    }


    inner class DownBinder : Binder() {
        fun startDownload() {
            this@MyService.startDownload()
        }

        fun getProgress(): Int {
            return this@MyService.getProgress()
        }
    }
}
