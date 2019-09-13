package org.javamaster.fragmentlearning.enums

import org.javamaster.fragmentlearning.R
import java.util.*

/**
 * @author yudong
 * @date 2019/8/10
 */
enum class TopicsTypeEnum(val code: Int, val msg: Int) {
    CODING(1, R.string.topics_type_coding),
    WEB(2, R.string.topics_type_web),
    LANG(3, R.string.topics_type_lang),
    DATA(4, R.string.topics_type_data),
    DEV(5, R.string.topics_type_dev);


    companion object {
        private val MAP: MutableMap<Int, TopicsTypeEnum>

        init {
            MAP = HashMap(6, 1f)
            for (value in values()) {
                MAP[value.code] = value
            }
        }

        fun getEnumByCode(code: Int?): TopicsTypeEnum? {
            return MAP[code]
        }
    }

}
