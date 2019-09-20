package org.javamaster.fragmentlearning.data

import org.javamaster.fragmentlearning.adapter.Type

/**
 * @author yudong
 * @date 2019/9/20
 */
data class ActivityExamingState(
    var showSubmitButton: Boolean = false,
    var showReturnSubmitButton: Boolean = false,
    var showQuestionsDot: Boolean = true,
    var showNextButton: Boolean = true,
    /**
     * 是否已浏览完所有题目
     */
    var allQuestionsComplete: Boolean = false,
    var constructSubmitPage: Boolean = false,
    var type: Type = Type.SUBMIT,
    var position: Int = 0
)