package org.javamaster.fragmentlearning.ui.activities

import android.app.Activity
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_login.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.ui.login.LoginViewModel
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/8/18
 */
class LoginActivity : BaseAppActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//      这一行一定要在setContentView后面,否则绑定会失败
        ButterKnife.bind(this)
        DaggerAppComponent.create().inject(this)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
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

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val result = it ?: return@Observer

            loading.visibility = View.GONE
            if (!result.success) {
                showLoginFailed(result.errorMsg!!)
                return@Observer
            }
            updateUiWithUser(result.data!!)
            setResult(Activity.RESULT_OK)
//            TODO
//            finish()
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
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
