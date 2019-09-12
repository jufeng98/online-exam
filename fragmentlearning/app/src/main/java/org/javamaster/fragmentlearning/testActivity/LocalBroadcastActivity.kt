package org.javamaster.fragmentlearning.testActivity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import butterknife.ButterKnife
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.consts.ActionConsts
import org.javamaster.fragmentlearning.testBroadcastReceiver.MyLocalReceiver

/**
 * 本地广播,只会在本程序内传播,无法静态注册
 */
class LocalBroadcastActivity : AppCompatActivity() {

    private lateinit var localBroadcastReceiver: MyLocalReceiver
    private lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_broadcast)
        ButterKnife.bind(this)
        localBroadcastReceiver = MyLocalReceiver()
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        var intentFilter = IntentFilter(ActionConsts.LOCAL_MSG)
        localBroadcastManager.registerReceiver(localBroadcastReceiver, intentFilter)
    }

    @OnClick(R.id.send_local_broadcast)
    fun sendBroadcast(view: View) {
        var intent = Intent(ActionConsts.LOCAL_MSG)
        intent.putExtra("myOwnString", "hello world")
        localBroadcastManager.sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver)
    }
}
