package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about.*
import org.javamaster.fragmentlearning.BuildConfig
import org.javamaster.fragmentlearning.R

class AboutActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_about
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        version.text = BuildConfig.VERSION_NAME
        confirm.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}
