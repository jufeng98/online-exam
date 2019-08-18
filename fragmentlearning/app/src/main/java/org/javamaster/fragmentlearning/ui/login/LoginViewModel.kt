package org.javamaster.fragmentlearning.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.LoginFormState
import org.javamaster.fragmentlearning.data.LoginService
import org.javamaster.fragmentlearning.data.model.Result
import org.javamaster.fragmentlearning.data.model.User
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/8/18
 */
class LoginViewModel @Inject constructor(private val LoginService: LoginService) : ViewModel() {

    private val usernamePattern = Pattern.compile("^[0-9a-zA-Z]+\$")
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Result<User>>()
    val loginResult: LiveData<Result<User>> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        var result: Result<User> = LoginService.login(username, password)
        _loginResult.value = result
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return usernamePattern.matcher(username).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length in 5..20
    }
}
