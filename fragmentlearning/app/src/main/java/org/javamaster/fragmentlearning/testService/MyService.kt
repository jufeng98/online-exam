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

class MyService : Service() {
    var mBinder = DownBinder()
    override fun onCreate() {
        super.onCreate()
        Log.i(this::class.qualifiedName, "service first started")
        val intent = Intent(this, SaveAndLoadActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this)
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentIntent(pi)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(
                    BitmapFactory.decodeResource(
                        resources,
                        R.mipmap.ic_launcher
                    )
                )
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(this::class.qualifiedName, "service started")
        Thread {
            //            耗时工作
//            自动停止
//            stopSelf()
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this::class.qualifiedName, "service destroyed")
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }
}

class DownBinder : Binder() {
    private var progress: Int = 0
    fun startDownload() {
        Log.i(this::class.qualifiedName, "start download")
        Thread {
            while (progress <= 100) {
                TimeUnit.SECONDS.sleep(1)
                progress += 10
            }
        }.start()
    }

    fun progress(): Int {
        return progress
    }
}