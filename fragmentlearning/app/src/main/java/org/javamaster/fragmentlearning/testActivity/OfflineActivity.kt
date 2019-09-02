package org.javamaster.fragmentlearning.testActivity

import android.content.Context
import android.content.Intent
import android.view.View
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.consts.ActionConsts
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class OfflineActivity : BaseAppActivity() {
    override fun initContentView(): Int {
        return R.layout.activity_offline
    }

    @OnClick(R.id.offline)
    fun sendBroadcast(view: View) {
        var intent = Intent(ActionConsts.FORCE_OFFLINE)
        sendBroadcast(intent)
    }

    companion object {
        fun actionStart(context: Context) {
            var intent = Intent(context, OfflineActivity::class.java)
            context.startActivity(intent)
        }
    }
}
