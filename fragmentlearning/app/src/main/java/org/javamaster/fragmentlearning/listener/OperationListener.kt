package org.javamaster.fragmentlearning.listener

import android.util.Log
import android.widget.Toast
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.exception.BizException
import org.javamaster.fragmentlearning.exception.LoginException

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
        fun fail(e: Throwable) {
            val msg = e.message ?: App.context.getString(AppConsts.ERROR_MSG)
            Log.i(this::class.qualifiedName, "error:$msg")
            // 异常简单弹出错误信息提示
            when (e) {
                is LoginException -> {
                    // 登录失效,这里什么也不做
                }
                is BizException -> {
                    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun fail(resultVo: ResultVo<out Any>) {
            Log.i(this::class.qualifiedName, "${resultVo.errorMsg} ${resultVo.errorMsg}")
            if (resultVo.errorCode != AppConsts.LOGIN_ERROR_CODE) {
                Toast.makeText(App.context, resultVo.errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}