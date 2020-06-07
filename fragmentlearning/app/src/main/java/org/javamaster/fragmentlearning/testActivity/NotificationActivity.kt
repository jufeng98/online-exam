package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.core.app.NotificationCompat
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.io.File


class NotificationActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_notice
    }


    @OnClick(R.id.send_notification1)
    fun send1() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, SaveAndLoadActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this)
            .setContentTitle("这是通知标题")
            .setContentText("这是通知内容这是通知内容")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_menu_mini)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_menu))
            .setContentIntent(pi)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()
        manager.notify(1, notification)
    }

    @OnClick(R.id.send_notification2)
    fun send2() {
        var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, SaveAndLoadActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this)
            .setContentTitle("这是通知标题")
            .setContentText("这是通知内容这是通知内容")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_menu_mini)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_menu))
            .setContentIntent(pi)
            .setStyle(
                NotificationCompat
                    .BigTextStyle()
                    .bigText(
                        "Learn how to build notifications, send and sync data, and use voice actions." +
                                " Get the official Android IDE and developer tools to build apps for Android."
                    )
            )
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()
        manager.notify(2, notification)
    }

    @OnClick(R.id.send_notification3)
    fun send3() {
        val intent = Intent(this, SaveAndLoadActivity::class.java)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this)
            .setContentTitle("这是通知标题")
            .setContentText("这是通知内容这是通知内容")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_menu_mini)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_menu))
            .setContentIntent(pi)
            .setSound(Uri.fromFile(File("/system/media/audio/ringtones/Luna.ogg")))
            .setVibrate(longArrayOf(0L, 1000L, 1000L, 1000L))
            .setLights(Color.GREEN, 1000, 1000)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(
                        BitmapFactory.decodeResource(resources, R.drawable.banana)
                    )
            )
            .setColor(resources.getColor(R.color.colorAppPrimary))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()
        manager.notify(3, notification)
    }

    companion object {
        fun actionStart(context: Activity) {
            val intent = Intent(context, NotificationActivity::class.java)
            context.startActivity(intent)
        }
    }
}
