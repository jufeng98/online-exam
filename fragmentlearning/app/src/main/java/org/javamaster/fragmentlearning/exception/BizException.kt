package org.javamaster.fragmentlearning.exception

/**
 * @author yudong
 * @date 2019/8/18
 */
class BizException(val errorCode: Int, private val errorMsg: String) : RuntimeException(errorMsg) {
    override fun toString(): String {
        return "errorCode:$errorCode,errorMsg:${super.toString()}"
    }
}