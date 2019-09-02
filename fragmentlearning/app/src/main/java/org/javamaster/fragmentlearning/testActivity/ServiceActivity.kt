package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_service.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.testService.DownBinder
import org.javamaster.fragmentlearning.testService.MyIntentService
import org.javamaster.fragmentlearning.testService.MyService
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class ServiceActivity : BaseAppActivity() {
    lateinit var downloadBinder: DownBinder
    var conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            downloadBinder = service as DownBinder
            downloadBinder.startDownload()
            progressBar2.progress = downloadBinder.progress()
        }

    }

    override fun initContentView(): Int? {
        return R.layout.activity_service
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @OnClick(R.id.button9, R.id.button10)
    fun handler(view: View) {
        when (view.id) {
            R.id.button9 -> {
                var intent = Intent(this@ServiceActivity, MyService::class.java)
                startService(intent)
            }
            R.id.button10 -> {
                var intent = Intent(this@ServiceActivity, MyService::class.java)
                stopService(intent)
            }
        }
    }

    @OnClick(R.id.button11, R.id.button12)
    fun binder(view: View) {
        when (view.id) {
            R.id.button11 -> {
                var intent = Intent(this@ServiceActivity, MyService::class.java)
                bindService(intent, conn, Context.BIND_AUTO_CREATE)
            }
            R.id.button12 -> {
                unbindService(conn)
            }
        }
    }

    @OnClick(R.id.button13)
    fun intentService(view: View) {
        when (view.id) {
            R.id.button13 -> {
                MyIntentService.startActionBaz(this@ServiceActivity, "", "")
            }
        }
    }

    companion object {
        fun actonStart(context: Activity) {
            var intent = Intent(context, ServiceActivity::class.java)
            context.startActivity(intent)
        }
    }
}
