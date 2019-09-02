package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_scheduled.*
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.testService.ScheduledIntentService
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity


class ScheduledActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return org.javamaster.fragmentlearning.R.layout.activity_scheduled
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @OnClick(org.javamaster.fragmentlearning.R.id.button15)
    fun start() {
        ScheduledIntentService.startActionFoo(this, object : OperationListener<String> {
            override fun success(t: String) {
                textView18.text = t
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        var intent = Intent(this, ScheduledIntentService.javaClass)
        stopService(intent)
    }

    companion object {
        fun actonStart(context: Activity) {
            var intent = Intent(context, ScheduledActivity::class.java)
            context.startActivity(intent)
        }
    }
}
