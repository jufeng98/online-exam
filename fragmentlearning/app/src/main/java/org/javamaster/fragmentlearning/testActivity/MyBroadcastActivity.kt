package org.javamaster.fragmentlearning.testActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindString
import butterknife.ButterKnife
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.consts.ActionConsts

class MyBroadcastActivity : AppCompatActivity() {

    @BindString(R.string.my_broadcast_msg)
    lateinit var msg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_broadcast)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.send_broadcast)
    fun sendBroadcast(view: View) {
        var intent = Intent("org.javamaster.fragmentlearning.MY_BROADCAST_MSG")
        intent.putExtra("myOwnString", msg)
        // 发送标准广播
        sendBroadcast(intent)
        // 发送有序广播
//        sendBroadcast(intent, null)
    }

    @OnClick(R.id.offline)
    fun sendBroadcast1(view: View) {
        var intent = Intent(ActionConsts.FORCE_OFFLINE)
        sendBroadcast(intent)
    }
}
