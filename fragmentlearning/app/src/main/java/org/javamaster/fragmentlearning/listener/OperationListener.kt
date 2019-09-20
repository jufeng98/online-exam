package org.javamaster.fragmentlearning.listener

import android.util.Log
import android.widget.Toast
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.model.ResultVo

/**
 * @author yudong
 * @date 2019/9/2
 */
interface OperationListener<T> {
    fun success(t: T)
    fun fail(errorCode: Int, errorMsg: String) {
        Log.i(this::class.qualifiedName, "$errorMsg $errorMsg")
        if (errorCode != AppConsts.LOGIN_ERROR_CODE) {
            Toast.makeText(App.context, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun fail(resultVo: ResultVo<out Any>) {
            Log.i(this::class.qualifiedName, "${resultVo.errorMsg} ${resultVo.errorMsg}")
            if (resultVo.errorCode != AppConsts.LOGIN_ERROR_CODE) {
                Toast.makeText(App.context, resultVo.errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}