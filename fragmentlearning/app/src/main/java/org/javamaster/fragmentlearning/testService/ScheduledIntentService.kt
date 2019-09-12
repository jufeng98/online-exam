package org.javamaster.fragmentlearning.testService

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.os.SystemClock
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.testBroadcastReceiver.ScheduledReceiver
import java.text.SimpleDateFormat
import java.util.*


class ScheduledIntentService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Task().execute()

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        第一个参数是一个整型参数，用于指定 AlarmManager的
//        工作类型，有四种值可选，分别是 ELAPSED_REALTIME、ELAPSED_REALTIME_WAKEUP、
//        RTC 和 RTC_WAKEUP。其中 ELAPSED_REALTIME 表示让定时任务的触发时间从系统开
//        机开始算起，但不会唤醒 CPU。ELAPSED_REALTIME_WAKEUP 同样表示让定时任务的触
//        发时间从系统开机开始算起，但会唤醒 CPU。RTC表示让定时任务的触发时间从 1970 年 1
//        月 1 日 0点开始算起，但不会唤醒 CPU。RTC_WAKEUP 同样表示让定时任务的触发时间从
//        1970 年 1 月 1 日 0 点开始算起，但会唤醒 CPU。使用 SystemClock.elapsedRealtime()方法可
//        以获取到系统开机至今所经历时间的毫秒数，使用 System.currentTimeMillis()方法可以获取
//        到 1970年 1 月 1日 0点至今所经历时间的毫秒数。
        val triggerAtTime = SystemClock.elapsedRealtime() + 5 * 1000
        val intent = Intent(this, ScheduledReceiver::class.java)
        val pi = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT)
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi)
//        要求 Alarm任务的执行时间必须准备无误
//        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi)
        return super.onStartCommand(intent, flags, startId)
    }

    class Task : AsyncTask<Void, Int, String>() {
        override fun doInBackground(vararg params: Void?): String {
            var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE)
            return format.format(Date())
        }

        override fun onPostExecute(result: String?) {
            listener.success(result!!)
        }
    }

    companion object {
        lateinit var listener: OperationListener<String>
        @JvmStatic
        fun startActionFoo(
            context: Context,
            listener: OperationListener<String>
        ) {
            val intent = Intent(context, ScheduledIntentService::class.java)
            ScheduledIntentService.listener = listener
            context.startService(intent)
        }
    }
}
