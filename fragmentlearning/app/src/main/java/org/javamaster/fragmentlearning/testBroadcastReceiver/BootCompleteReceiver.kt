package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "开机启动完成", Toast.LENGTH_LONG).show()
    }
}
