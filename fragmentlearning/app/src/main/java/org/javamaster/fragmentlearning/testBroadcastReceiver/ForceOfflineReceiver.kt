package org.javamaster.fragmentlearning.testBroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.ui.activities.LoginActivity

/**
 * 登录失效强制下线用户
 * @author yudong
 * @date 2019/8/24
 */
class ForceOfflineReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alertDialog = AlertDialog.Builder(App.context)
        alertDialog.setTitle(R.string.dialog_title_warm_tip)
        alertDialog.setMessage(R.string.login_invalided)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(R.string.dialog_confirm) { _, _ ->
            App.finishAll()
            LoginActivity.actionStart(context)
        }
        alertDialog.show()
    }

}
