package org.javamaster.fragmentlearning.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.LoginService
import org.javamaster.fragmentlearning.data.SignupFormState
import org.javamaster.fragmentlearning.data.model.CreateOrEditUsersForm
import org.javamaster.fragmentlearning.data.model.CreateUsersReqVo
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.ui.login.LoginViewModel
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/9/3
 */
class SignupViewModel @Inject constructor(private val LoginService: LoginService) : ViewModel() {
    private var _signupFormState = MutableLiveData<SignupFormState>()
    var signupFormState: LiveData<SignupFormState> = _signupFormState

    private var _signupResult = MutableLiveData<ResultVo<User>>()
    var signupResult: LiveData<ResultVo<User>> = _signupResult

    fun signupDataChange(email: String, username: String, password: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _signupFormState.value = SignupFormState(emailError = R.string.sign_up_email_error)
        } else if (!LoginViewModel.isUserNameValid(username)) {
            _signupFormState.value = SignupFormState(usernameError = R.string.invalid_username)
        } else if (!LoginViewModel.isPasswordValid(password)) {
            _signupFormState.value = SignupFormState(passwordError = R.string.invalid_password)
        } else {
            _signupFormState.value = SignupFormState(isDataValid = true)
        }
    }

    fun signup(email: String, username: String, password: String) {
        var createUsersReqVo = CreateUsersReqVo()
        var createOrEditUsersForm = CreateOrEditUsersForm()
        createUsersReqVo.createOrEditUsersForm = createOrEditUsersForm
        createOrEditUsersForm.email = email
        createOrEditUsersForm.username = username
        createOrEditUsersForm.password = password
        Thread {
            var resultVo = LoginService.signUp(createUsersReqVo)
            _signupResult.postValue(resultVo)
        }.start()
    }
}