package org.javamaster.fragmentlearning.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import butterknife.OnClick
import org.javamaster.fragmentlearning.R

class SignUpActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_sign_up
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @OnClick(R.id.user_term)
    fun showUserTerm() {
        var intent = Intent()
        intent.data = Uri.parse("https://www.baidu.com")
        intent.action = Intent.ACTION_VIEW
        this.startActivity(intent)
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }
}
