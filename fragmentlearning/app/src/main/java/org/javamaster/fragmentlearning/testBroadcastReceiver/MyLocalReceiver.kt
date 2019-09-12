package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyLocalReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "来自本地广播接收器的消息:" + intent.getStringExtra("myOwnString"), Toast.LENGTH_LONG).show()
    }
}
