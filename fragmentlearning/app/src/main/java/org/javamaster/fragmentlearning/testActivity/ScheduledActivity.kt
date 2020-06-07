package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_scheduled.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.testService.ScheduledIntentService
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity


class ScheduledActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_scheduled
    }

    @OnClick(R.id.button15)
    fun start() {
        ScheduledIntentService.startActionFoo(this, object : OperationListener<String> {
            override fun success(t: String) {
                textView18.text = t
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this, ScheduledIntentService::class.java)
        stopService(intent)
    }

    companion object {
        fun actonStart(context: Activity) {
            val intent = Intent(context, ScheduledActivity::class.java)
            context.startActivity(intent)
        }
    }
}
