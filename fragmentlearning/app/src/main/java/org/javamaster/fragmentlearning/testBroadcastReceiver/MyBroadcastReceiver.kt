package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var msg = intent.getStringExtra("myOwnString")
        Toast.makeText(context, "the message is:$msg", Toast.LENGTH_LONG).show()
        // 阻断有序广播
//        abortBroadcast()
    }
}
