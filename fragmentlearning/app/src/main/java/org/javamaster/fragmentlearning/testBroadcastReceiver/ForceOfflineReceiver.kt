package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.ui.activities.LoginActivity

class ForceOfflineReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var alertDialog = androidx.appcompat.app.AlertDialog.Builder(context)
        alertDialog.setTitle("waring")
        alertDialog.setMessage("登录失效,请重新登录")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK") { _, _ ->
            App.finishAll()
            var intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
        alertDialog.show()
    }
}
