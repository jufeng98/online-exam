package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.javamaster.fragmentlearning.testService.ScheduledIntentService

class ScheduledReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        ScheduledIntentService.startActionFoo(context, ScheduledIntentService.listener)
    }
}
