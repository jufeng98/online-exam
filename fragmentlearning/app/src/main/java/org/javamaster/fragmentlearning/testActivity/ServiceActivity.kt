package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.view.View
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_service.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.testService.MyIntentService
import org.javamaster.fragmentlearning.testService.MyService
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class ServiceActivity : BaseAppActivity() {
    lateinit var downloadBinder: MyService.DownBinder
    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            downloadBinder = service as MyService.DownBinder
            downloadBinder.startDownload()
            progressBar2.progress = downloadBinder.getProgress()
        }

    }

    override fun initContentView(): Int? {
        return R.layout.activity_service
    }

    @OnClick(R.id.button9, R.id.button10)
    fun handler(view: View) {
        when (view.id) {
            R.id.button9 -> {
                val intent = Intent(this, MyService::class.java)
                startService(intent)
            }
            R.id.button10 -> {
                val intent = Intent(this, MyService::class.java)
                stopService(intent)
            }
        }
    }

    @OnClick(R.id.button11, R.id.button12)
    fun binder(view: View) {
        when (view.id) {
            R.id.button11 -> {
                val intent = Intent(this, MyService::class.java)
                bindService(intent, conn, Context.BIND_AUTO_CREATE)
            }
            R.id.button12 -> {
                try {
                    unbindService(conn)
                } catch (e: Exception) {
                }
            }
        }
    }

    @OnClick(R.id.button18)
    fun startDownload() {
        downloadBinder.startDownload()
    }

    @OnClick(R.id.button13)
    fun intentService(view: View) {
        when (view.id) {
            R.id.button13 -> {
                MyIntentService.startActionBaz(this, "", "")
            }
        }
    }

    companion object {
        fun actonStart(context: Activity) {
            val intent = Intent(context, ServiceActivity::class.java)
            context.startActivity(intent)
        }
    }
}
