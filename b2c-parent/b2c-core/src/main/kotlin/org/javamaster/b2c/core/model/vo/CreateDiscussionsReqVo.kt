package org.javamaster.b2c.core.model.vo

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

/**
 *
 * @author yudong
 * @date 2019/10/9
 */
data class CreateDiscussionsReqVo(
        @NotBlank var question: String = "",
        var description: String = "",
        @Length(min = 1) var relevantTags: List<String> = mutableListOf()
)