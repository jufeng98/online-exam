package org.javamaster.fragmentlearning.enums

/**
 * @author yudong
 * @date 2019/9/20
 */
enum class ReadEnum constructor(private val code: Int, private val msg: String) : EnumBase {
    UNREAD(1, "未读"),
    READ(2, "已读");


    override fun getCode(): Int {
        return code
    }

    override fun getMsg(): String {
        return msg
    }

}
