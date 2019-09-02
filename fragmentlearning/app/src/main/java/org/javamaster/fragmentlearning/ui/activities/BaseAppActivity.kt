package org.javamaster.fragmentlearning.ui.activities

import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.ActionConsts
import org.javamaster.fragmentlearning.testBroadcastReceiver.ForceOfflineReceiver

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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
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
}
