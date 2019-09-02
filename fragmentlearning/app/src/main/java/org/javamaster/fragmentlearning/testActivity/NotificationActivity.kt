package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
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

    @OnClick(R.id.send_notification)
    fun send() {
        val intent = Intent(this, SaveAndLoadActivity::class.java)
        var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this)
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentIntent(pi)
            .setSound(Uri.fromFile(File("/system/media/audio/ringtones/Luna.ogg")))
            //        .setVibrate(new long[]{0, 1000, 1000, 1000})
            //        .setLights(Color.GREEN, 1000, 1000)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            //        .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
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
        manager.notify(1, notification)
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, NotificationActivity::class.java)
            context.startActivity(intent)
        }
    }
}
