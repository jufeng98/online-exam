package org.javamaster.fragmentlearning.data

/**
 * @author yudong
 * @date 2019/8/18
 */
data class SignupFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val emailError: Int? = null,
    val isDataValid: Boolean = false
)
