package org.javamaster.fragmentlearning.service

import org.javamaster.fragmentlearning.data.entity.Knowledges
import org.javamaster.fragmentlearning.data.entity.Sections
import org.javamaster.fragmentlearning.data.entity.Topics
import org.javamaster.fragmentlearning.data.model.ResultVo

/**
 * @author yudong
 * @date 2019/8/18
 */
interface LearnService {

    fun findTopicsList(): ResultVo<List<Topics>>

    fun findSectionsList(topicsCode: String): ResultVo<List<Sections>>

    fun findKnowledgesList(sectionsCode: String): ResultVo<List<Knowledges>>

    fun findKnowledgesQuestionNum(): ResultVo<Map<String, Int>>
}