package org.javamaster.fragmentlearning.data.model

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * @author yudong
 * @date 2019/8/18
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class ResultVo<T> constructor(
    val success: Boolean = false,
    val errorCode: Int? = null,
    val errorMsg: String? = "",
    val data: T? = null,
    val total: Long? = null
)
