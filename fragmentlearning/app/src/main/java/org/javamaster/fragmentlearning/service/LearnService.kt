package org.javamaster.fragmentlearning.service

import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.*
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.litepal.LitePal

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

    fun findQuestionsByExamsCode(examsCode: String): ResultVo<List<ExamQuestionsVo>>

    fun saveLearns(knowledgePointsCode: String): ResultVo<Int>

    fun findTopicsProgress(): ResultVo<Map<String, Int>>

    fun findSectionsProgress(): ResultVo<Map<String, Int>>

    companion object {
        fun getTopicsProgressMap(): Map<String, Int> {
            val username = App.getLoginSharedPreferences().getString(LoginService.USERNAME, "")
            val progressList = LitePal.where("username=?", username).find(TopicsProgressVo::class.java)
            val map = mutableMapOf<String, Int>()
            progressList.forEach {
                map[it.topicsCode] = it.progress
            }
            return map
        }

        fun getSectionsProgressMap(): Map<String, Int> {
            val username = App.getLoginSharedPreferences().getString(LoginService.USERNAME, "")
            val progressList = LitePal.where("username=?", username).find(SectionsProgressVo::class.java)
            val map = mutableMapOf<String, Int>()
            progressList.forEach {
                map[it.sectionsCode] = it.progress
            }
            return map
        }
    }
}