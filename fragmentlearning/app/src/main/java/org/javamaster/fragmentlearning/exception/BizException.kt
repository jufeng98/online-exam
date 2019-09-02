package org.javamaster.fragmentlearning.exception

/**
 * @author yudong
 * @date 2019/8/18
 */
class BizException(private val errorCode: Int, errorMsg: String) : RuntimeException(errorMsg) {
    override fun toString(): String {
        return "errorCode:$errorCode,errorMsg:${super.toString()}"
    }
}