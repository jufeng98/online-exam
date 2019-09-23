package org.javamaster.fragmentlearning.service

import org.javamaster.fragmentlearning.data.entity.*
import org.javamaster.fragmentlearning.data.model.ExamsAnswer
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.SubmitAnswersResVo
import org.litepal.LitePal

/**
 * @author yudong
 * @date 2019/8/18
 */
interface LearnService {

    fun findTopicsList(cacheFirst: Boolean): MutableList<Topics>

    fun findSectionsList(topicsCode: String, cacheFirst: Boolean): MutableList<Sections>

    fun findKnowledgesList(sectionsCode: String, cacheFirst: Boolean): MutableList<Knowledges>

    fun findKnowledgesQuestionNum(sectionsCode: String, cacheFirst: Boolean): MutableMap<String, Int>

    fun findKnowledgePointsList(knowledgesCode: String): ResultVo<List<KnowledgePoints>>

    fun findQuestionsList(questionsCode: String): ResultVo<List<Questions>>

    fun findAssociateOptions(questionsCode: String): ResultVo<List<Options>>

    fun findExamsList(cacheFirst: Boolean): MutableList<Exams>

    fun findQuestionsByExamsCode(examsCode: String): ResultVo<List<ExamQuestionsVo>>

    fun saveLearns(knowledgePointsCode: String): ResultVo<Int>

    fun findTopicsProgress(cacheFirst: Boolean): Map<String, Int>

    fun findSectionsProgress(topicsCode: String, cacheFirst: Boolean): MutableMap<String, Int>

    fun submitAnswers(examsCode: String, examsAnswers: List<ExamsAnswer>): ResultVo<SubmitAnswersResVo>

    companion object {
        fun getExamQuestionsVos(examsCode: String): List<ExamQuestionsVo> {
            val examQuestionsVos = LitePal.where("examsCode=?", examsCode).find(ExamQuestionsVo::class.java)
            if (examQuestionsVos.isNotEmpty()) {
                examQuestionsVos.forEach {
                    it.optionsVos = LitePal.where("questionsCode=?", it.questionsCode).find(OptionsVo::class.java)
                }
            }
            return examQuestionsVos
        }
    }
}