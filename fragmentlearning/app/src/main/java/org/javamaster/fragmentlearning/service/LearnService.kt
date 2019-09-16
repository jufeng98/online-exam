package org.javamaster.fragmentlearning.service

import org.javamaster.fragmentlearning.data.entity.*
import org.javamaster.fragmentlearning.data.model.ResultVo

/**
 * @author yudong
 * @date 2019/8/18
 */
interface LearnService {

    fun findTopicsList(): ResultVo<List<Topics>>

    fun findSectionsList(topicsCode: String): ResultVo<List<Sections>>

    fun findKnowledgesList(sectionsCode: String): ResultVo<List<Knowledges>>

    fun findKnowledgesQuestionNum(sectionsCode: String): ResultVo<List<KnowledgesQuestionNumVo>>

    fun findKnowledgePointsList(knowledgesCode: String): ResultVo<List<KnowledgePoints>>

    fun findQuestionsList(questionsCode: String): ResultVo<List<Questions>>

    fun findAssociateOptions(questionsCode: String): ResultVo<List<Options>>

    fun findExamsList(): ResultVo<List<Exams>>
}