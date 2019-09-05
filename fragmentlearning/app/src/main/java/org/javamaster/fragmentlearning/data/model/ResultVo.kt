package org.javamaster.fragmentlearning.data.model

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * @author yudong
 * @date 2019/8/18
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class ResultVo<T> constructor(
    val success: Boolean,
    val errorCode: Int?,
    val errorMsg: String?,
    val data: T?,
    val total: Long?
) {
    constructor() : this(false, null, null, null, null)
    constructor(
        errorCode: Int?,
        errorMsg: String?
    ) : this(false, errorCode, errorMsg, null, null)
}
