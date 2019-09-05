package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.widget.Toast
import butterknife.OnClick
import butterknife.OnTextChanged
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import org.javamaster.fragmentlearning.R

class ForgetPwdActivity : BaseAppActivity() {
    var sendEmailCurrentTextColor = 0
    override fun initContentView(): Int? {
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        return R.layout.activity_forget_pwd
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendEmailCurrentTextColor = send_email.currentTextColor
    }

    @OnClick(R.id.cancel_send)
    fun cancelSend() {
        finish()
    }

    @OnTextChanged(R.id.input_email)
    fun dataChange(text: CharSequence) {
        if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            send_email.isEnabled = false
            send_email.setTextColor(sendEmailCurrentTextColor)
            input_email.error = getString(R.string.sign_up_email_error)
            return
        }
        send_email.setTextColor(resources.getColor(R.color.colorAppPrimary))
        send_email.isEnabled = true
    }

    @OnClick(R.id.send_email)
    fun sendEmail() {
        Toast.makeText(this@ForgetPwdActivity, R.string.app_completing, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun actionStart(context: Context) {
            var intent = Intent(context, ForgetPwdActivity::class.java)
            context.startActivity(intent)
        }
    }
}
