package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.OnClick
import butterknife.OnTextChanged
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.ui.signup.SignupViewModel
import javax.inject.Inject

class SignUpActivity : BaseAppActivity() {
    @Inject
    lateinit var signupViewModel: SignupViewModel

    override fun initContentView(): Int? {
        return R.layout.activity_sign_up
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        signupViewModel.signupFormState.observe(this, Observer {
            sign_up.isEnabled = it.isDataValid
            if (it.isDataValid) return@Observer
            if (it.emailError != null) {
                sign_up_email.error = getString(it.emailError)
            }
            if (it.usernameError != null) {
                sign_up_name.error = getString(it.usernameError)
            }
            if (it.passwordError != null) {
                sign_up_password.error = getString(it.passwordError)
            }
        })
        signupViewModel.signupResult.observe(this, Observer {
            sign_up_loading.visibility = View.GONE
            if (!it.success) {
                Toast.makeText(this, it.errorMsg, Toast.LENGTH_LONG).show()
                return@Observer
            }
            MainActivity.actionStart(this)
            finish()
        })
    }

    @OnTextChanged(R.id.sign_up_email, R.id.sign_up_name, R.id.sign_up_password)
    fun dataChange() {
        signupViewModel.signupDataChange(
            sign_up_email.text.toString(),
            sign_up_name.text.toString(),
            sign_up_password.text.toString()
        )
    }

    @OnClick(R.id.sign_up)
    fun signUp() {
        sign_up_loading.visibility = View.VISIBLE
        signupViewModel.signup(
            sign_up_email.text.toString(),
            sign_up_name.text.toString(),
            sign_up_password.text.toString()
        )
    }

    @OnClick(R.id.user_term)
    fun showUserTerm() {
        var intent = Intent()
        intent.data = Uri.parse("https://www.baidu.com")
        intent.action = Intent.ACTION_VIEW
        startActivity(intent)
    }

    @OnClick(R.id.sign_up_wechat, R.id.sign_up_qq)
    fun signupFromTecent() {
        Toast.makeText(this, R.string.app_completing, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun actionStart(context: Context) {
            var intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }
}
