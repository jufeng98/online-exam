package org.javamaster.fragmentlearning.data

/**
 * @author yudong
 * @date 2019/10/10
 */
data class DiscussFormState(
    val questionError: Int? = null,
    val relevantTagsError: Int? = null,
    val isDataValid: Boolean = false
)
