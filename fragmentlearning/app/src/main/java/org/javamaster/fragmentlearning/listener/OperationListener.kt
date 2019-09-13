package org.javamaster.fragmentlearning.listener

import android.util.Log

/**
 * @author yudong
 * @date 2019/9/2
 */
interface OperationListener<T> {
    fun success(t: T)
    fun fail(errorCode: Int, errorMsg: String) {
        Log.i(this::class.qualifiedName, "$errorMsg $errorMsg")
    }
}