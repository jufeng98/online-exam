package org.javamaster.fragmentlearning.testActivity

import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.javamaster.fragmentlearning.testBroadcastReceiver.NetWorkChangeReceiver

/**
 * 动态注册广播接收器监听网络变化,需在onDestroy中取消注册
 * @author yudong
 * @date 2019/8/28
 */
class NetWorkChangeReceiverActivity : AppCompatActivity() {
    private lateinit var receiver: NetWorkChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = TextView(this)
        view.text = "请尝试改变手机网络状态"
        setContentView(view)
        var intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        receiver = NetWorkChangeReceiver()
        registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}
