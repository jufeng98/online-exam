package org.javamaster.fragmentlearning.data

/**
 * Data validation state of the login form.
 * @author yudong
 * @date 2019/8/18
 */
data class SignupFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val emailError: Int? = null,
    val isDataValid: Boolean = false
)
