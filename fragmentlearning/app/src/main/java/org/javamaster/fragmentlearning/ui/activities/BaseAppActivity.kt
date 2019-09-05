package org.javamaster.fragmentlearning.ui.activities

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Process.killProcess
import android.os.Process.myPid
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.ActionConsts
import org.javamaster.fragmentlearning.testBroadcastReceiver.ForceOfflineReceiver
import java.util.concurrent.TimeUnit


/**
 * @author yudong
 * @date 2019/8/18
 */
abstract class BaseAppActivity : AppCompatActivity() {

    private lateinit var offlineReceiver: ForceOfflineReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewId = initContentView()
        if (viewId != null) setContentView(viewId)
        //  这一行一定要在setContentView后面,否则绑定可能会失败
        ButterKnife.bind(this)
        App.addActivity(this)
    }

    abstract fun initContentView(): Int?

    override fun onResume() {
        super.onResume()
        var intentFilter = IntentFilter(ActionConsts.FORCE_OFFLINE)
        offlineReceiver = ForceOfflineReceiver()
        registerReceiver(offlineReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(offlineReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.removeActivity(this)
    }

    private var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode != KeyEvent.KEYCODE_BACK || event.action !== KeyEvent.ACTION_DOWN) {
            return super.onKeyDown(keyCode, event)
        }
        if (App.activities.size > 1) {
            return super.onKeyDown(keyCode, event)
        }
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(applicationContext, R.string.hint_twice_quit, Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
            return true
        }
        // 实现温和退出程序
        val intent = Intent(Intent.ACTION_MAIN)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
        runOnUiThread {
            TimeUnit.SECONDS.sleep(1)
            App.finishAll()
            killProcess(myPid())
            System.exit(0)
        }
        return true
    }
}
