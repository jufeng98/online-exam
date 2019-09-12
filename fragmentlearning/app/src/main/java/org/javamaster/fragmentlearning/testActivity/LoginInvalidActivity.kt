package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_invalid.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class LoginInvalidActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_login_invalid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button17.setOnClickListener {
            finish()
        }
    }
}
