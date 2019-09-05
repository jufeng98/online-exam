package org.javamaster.fragmentlearning.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.LoginFormState
import org.javamaster.fragmentlearning.data.LoginService
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.User
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/8/18
 */
class LoginViewModel @Inject constructor(private val LoginService: LoginService) : ViewModel() {

    private val _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    private val _loginResult = MutableLiveData<ResultVo<User>>()
    val loginResultVo: LiveData<ResultVo<User>> = _loginResult

    fun login(username: String, password: String) {
        var resultVo: ResultVo<User>
        Thread {
            resultVo = LoginService.login(username, password)
            _loginResult.postValue(resultVo)
        }.start()
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginFormState.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginFormState.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginFormState.value = LoginFormState(isDataValid = true)
        }
    }

    companion object {
        private val usernamePattern = Pattern.compile("^[0-9a-zA-Z]+\$")
        fun isUserNameValid(username: String): Boolean {
            return usernamePattern.matcher(username).matches()
        }

        fun isPasswordValid(password: String): Boolean {
            return password.length in 5..20
        }
    }

}
