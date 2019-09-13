package org.javamaster.fragmentlearning.exception

import java.io.IOException

/**
 * @author yudong
 * @date 2019/9/12
 */
class LoginException(val errorCode: Int, errorMsg: String) : IOException(errorMsg)