package org.javamaster.fragmentlearning.data.model

import org.javamaster.fragmentlearning.adapter.Type

/**
 * @author yudong
 * @date 2019/9/20
 */
data class AnswerResultState(
    var showError: Boolean? = null,
    var errorMsg: String? = null,
    var submitAnswersResVo: SubmitAnswersResVo? = null,
    var type: Type = Type.SUBMIT
)