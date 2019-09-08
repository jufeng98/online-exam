package org.javamaster.fragmentlearning.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_login.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.ui.login.LoginViewModel
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/8/18
 */
class LoginActivity : BaseAppActivity() {

    override fun initContentView(): Int {
        return R.layout.activity_login
    }

    //  @Named("dev")
    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.action_sign_in)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        var pre = getPreferences(Context.MODE_PRIVATE)
        username.setText(pre.getString(USERNAME, ""))
        password.setText(pre.getString(PASSWORD, ""))
        if (username.text.toString() != "" && password.text.toString() != "") {
            login.isEnabled = true
        }
        checkBox.isChecked = pre.getBoolean(REMEMBER_PWD, false)

        loginViewModel.loginFormState.observe(this, Observer {
            val state = it ?: return@Observer
            // disable login button unless both username / password is valid
            login.isEnabled = state.isDataValid
            if (state.usernameError != null) {
                username.error = getString(state.usernameError)
            }
            if (state.passwordError != null) {
                password.error = getString(state.passwordError)
            }
        })

        loginViewModel.loginResultVo.observe(this, Observer {
            loading.visibility = View.GONE
            if (!it.success) {
                if (it.errorMsg == App.context.getString(R.string.login_invalided)) {
                    return@Observer
                }
                showLoginFailed(it.errorMsg!!)
                return@Observer
            }
            updateUiWithUser(it.data!!)
            setResult(Activity.RESULT_OK)
            MainActivity.actionStart(this)
            App.finishExcept(mutableSetOf(MainActivity::class.java))
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }
        }
    }

    @OnClick(R.id.login)
    fun login() {
        loading.visibility = View.VISIBLE
        loginViewModel.login(username.text.toString(), password.text.toString())
    }

    @OnClick(R.id.forget_pwd)
    fun forgetPwd() {
        ForgetPwdActivity.actionStart(this)
    }

    @OnClick(R.id.sign_up_wechat, R.id.sign_up_qq)
    fun loginFromTecent() {
        Toast.makeText(this, R.string.app_completing, Toast.LENGTH_SHORT).show()
    }

    private fun updateUiWithUser(model: User) {
        val welcome = getString(R.string.welcome)
        val username = model.username
        Toast.makeText(
            applicationContext,
            "$welcome $username",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        var edit = getPreferences(Context.MODE_PRIVATE).edit()
        edit.putString(USERNAME, username.text.toString())
        edit.putBoolean(REMEMBER_PWD, checkBox.isChecked)
        // 演示使用,为了简单起见,密码直接明文保存
        if (checkBox.isChecked) {
            edit.putString(PASSWORD, password.text.toString())
        } else {
            edit.putString(PASSWORD, "")
        }
        edit.apply()
    }

    companion object {
        fun actionStart(context: Context) {
            var intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val REMEMBER_PWD = "rememberPwd"
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
