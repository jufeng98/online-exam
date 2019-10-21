package org.javamaster.fragmentlearning.service.impl

import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Response
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.entity.*
import org.javamaster.fragmentlearning.data.model.*
import org.javamaster.fragmentlearning.exception.BizException
import org.javamaster.fragmentlearning.exception.LoginException
import org.javamaster.fragmentlearning.service.LearnService
import org.javamaster.fragmentlearning.service.LoginService
import org.javamaster.fragmentlearning.utils.NetUtils
import org.litepal.LitePal

/**
 * @author yudong
 * @date 2019/8/18
 */
class LearnServiceImpl constructor(private val objectMapper: ObjectMapper) : LearnService {

    override fun findTopicsList(cacheFirst: Boolean): MutableList<Topics> {
        if (cacheFirst) {
            val topicsList = LitePal.findAll(Topics::class.java)
            if (topicsList.isNotEmpty()) {
                return topicsList
            }
        }
        val map = mutableMapOf<String, Any>()
        map["topicsForm"] = mapOf<String, Any>()
        map["page"] = getPage()
        val response = NetUtils.postForResponse(AppConsts.FIND_TOPICS_LIST, map)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<MutableList<Topics>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<MutableList<Topics>>>() {})
        if (resultVo.success) {
            LitePal.deleteAll(Topics::class.java)
            // 缓存到数据库
            LitePal.saveAll(resultVo.data!!)
            return resultVo.data
        }
        throw BizException(resultVo)
    }

    override fun findSectionsList(topicsCode: String, cacheFirst: Boolean): MutableList<Sections> {
        if (cacheFirst) {
            val sectionsList = LitePal.where("topicsCode=?", topicsCode).find(Sections::class.java)
            if (sectionsList.isNotEmpty()) {
                return sectionsList
            }
        }
        val map = mutableMapOf<String, Any>()
        map["sectionsForm"] = mapOf<String, Any>("topicsCode" to topicsCode)
        map["page"] = getPage()
        val response = NetUtils.postForResponse(AppConsts.FIND_SECTIONS_LIST, map)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<MutableList<Sections>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Sections>>>() {})
        if (resultVo.success) {
            LitePal.deleteAll(Sections::class.java, "topicsCode=?", topicsCode)
            // 缓存到数据库
            LitePal.saveAll(resultVo.data!!)
            return resultVo.data
        }
        throw BizException(resultVo)
    }

    override fun findKnowledgesList(sectionsCode: String, cacheFirst: Boolean): MutableList<Knowledges> {
        if (cacheFirst) {
            val knowledgesList = LitePal.where("sectionsCode=?", sectionsCode).find(Knowledges::class.java)
            if (knowledgesList.isNotEmpty()) {
                return knowledgesList
            }
        }
        val map = mutableMapOf<String, Any>()
        map["knowledgesForm"] = mapOf<String, Any>("sectionsCode" to sectionsCode)
        map["page"] = getPage()
        val response = NetUtils.postForResponse(AppConsts.FIND_KNOWLEDGES_LIST, map)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<MutableList<Knowledges>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<MutableList<Knowledges>>>() {})
        if (resultVo.success) {
            LitePal.deleteAll(Knowledges::class.java, "sectionsCode=?", sectionsCode)
            LitePal.saveAll(resultVo.data!!)
            return resultVo.data
        }
        throw BizException(resultVo)
    }

    override fun findKnowledgesQuestionNum(
        sectionsCode: String,
        cacheFirst: Boolean
    ): MutableMap<String, Int> {
        if (cacheFirst) {
            val list = LitePal.where("sectionsCode=?", sectionsCode).find(KnowledgesQuestionNumVo::class.java)
            if (list.isNotEmpty()) {
                val map = mutableMapOf<String, Int>()
                list.forEach {
                    map[it.sectionsCode] = it.questionsNum
                }
                return map
            }
        }
        val reqMap = mutableMapOf("sectionsCode" to sectionsCode)
        val response = NetUtils.postForResponse(AppConsts.FIND_KNOWLEDGES_QUESTION_NUM, reqMap)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<MutableList<KnowledgesQuestionNumVo>> = objectMapper.readValue(
            resJsonStr,
            object : TypeReference<ResultVo<MutableList<KnowledgesQuestionNumVo>>>() {})
        if (resultVo.success) {
            LitePal.deleteAll(KnowledgesQuestionNumVo::class.java, "sectionsCode=?", sectionsCode)
            LitePal.saveAll(resultVo.data!!)
            val map = mutableMapOf<String, Int>()
            resultVo.data.forEach {
                map[it.sectionsCode] = it.questionsNum
            }
            return map
        }
        throw BizException(resultVo)
    }

    override fun findKnowledgePointsList(knowledgesCode: String): ResultVo<List<KnowledgePoints>> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["knowledgePointsForm"] = mapOf<String, Any>("knowledgesCode" to knowledgesCode)
            map["page"] = getPage()
            response = NetUtils.postForResponse(AppConsts.FIND_KNOWLEDGE_POINTS_LIST, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<KnowledgePoints>>>() {})
    }

    override fun findQuestionsList(questionsCode: String): ResultVo<List<Questions>> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["questionsForm"] = mapOf<String, Any>("questionsCode" to questionsCode)
            map["page"] = getPage()
            response = NetUtils.postForResponse(AppConsts.FIND_QUESTIONS_LIST, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Questions>>>() {})
    }

    override fun findAssociateOptions(questionsCode: String): ResultVo<List<Options>> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["questionsCode"] = questionsCode
            response = NetUtils.postForResponse(AppConsts.FIND_OPTIONS_LIST, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Options>>>() {})
    }

    override fun findExamsList(cacheFirst: Boolean): MutableList<Exams> {
        if (cacheFirst) {
            val examsList = LitePal.findAll(Exams::class.java)
            if (examsList.isNotEmpty()) {
                return examsList
            }
        }
        val response: Response
        val map = mutableMapOf<String, Any>()
        map["page"] = getPage()
        response = NetUtils.postForResponse(AppConsts.FIND_EXAMS_LIST, map)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<MutableList<Exams>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Exams>>>() {})
        if (resultVo.success) {
            LitePal.deleteAll(Exams::class.java)
            LitePal.saveAll(resultVo.data!!)
            return resultVo.data
        }
        throw BizException(resultVo)
    }

    override fun findQuestionsByExamsCode(examsCode: String): ResultVo<List<ExamQuestionsVo>> {
        val response: Response
        try {
            val map = mutableMapOf("examsCode" to examsCode)
            response = NetUtils.postForResponse(AppConsts.FIND_QUESTIONS_BY_EXAMS_CODE, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<List<ExamQuestionsVo>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<ExamQuestionsVo>>>() {})
        if (resultVo.success) {
            LitePal.deleteAll(ExamQuestionsVo::class.java, "examsCode=?", examsCode)
            resultVo.data!!.forEach {
                LitePal.deleteAll(OptionsVo::class.java, "questionsCode=?", it.questionsCode)
                it.examsCode = examsCode
                it.optionsVos.forEach { optionsVo: OptionsVo ->
                    optionsVo.questionsCode = it.questionsCode
                }
                LitePal.saveAll(it.optionsVos)
            }
            LitePal.saveAll(resultVo.data)
        }
        return resultVo
    }

    override fun saveLearns(knowledgePointsCode: String): ResultVo<Int> {
        val response: Response
        val learnsRecordVo = LearnsRecordVo()
        val preference = App.getLearnSharedPreferences()
        learnsRecordVo.topicsCode = preference.getString("topicsCode", "")
        learnsRecordVo.sectionsCode = preference.getString("sectionsCode", "")
        learnsRecordVo.knowledgesCode = preference.getString("knowledgesCode", "")
        learnsRecordVo.username = App.getLoginSharedPreferences().getString(LoginService.USERNAME, "")
        learnsRecordVo.knowledgePointsCode = knowledgePointsCode
        try {
            val map = mutableMapOf<String, Any>()
            map["learnsRecordVo"] = learnsRecordVo
            response = NetUtils.postForResponse(AppConsts.SAVE_LEARNS, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<Int>>() {})
    }

    override fun findTopicsProgress(cacheFirst: Boolean): Map<String, Int> {
        val username = App.getLoginSharedPreferences().getString(LoginService.USERNAME, "")
        if (cacheFirst) {
            val progressList = LitePal.where("username=?", username).find(TopicsProgressVo::class.java)
            if (progressList.isNotEmpty()) {
                val map = mutableMapOf<String, Int>()
                progressList.forEach {
                    map[it.topicsCode] = it.progress
                }
                return map
            }
        }
        val response: Response
        val map1 = mutableMapOf("username" to username)
        response = NetUtils.postForResponse(AppConsts.FIND_TOPICS_PROGRESS, map1)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<List<TopicsProgressVo>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<TopicsProgressVo>>>() {})
        if (resultVo.success) {
            val list = resultVo.data!!
            LitePal.deleteAll(TopicsProgressVo::class.java, "username=?", username)
            LitePal.saveAll(list)
            val map = mutableMapOf<String, Int>()
            resultVo.data.forEach {
                map[it.topicsCode] = it.progress
            }
            return map
        }
        throw BizException(resultVo)
    }

    override fun findSectionsProgress(topicsCode: String, cacheFirst: Boolean): MutableMap<String, Int> {
        if (cacheFirst) {
            val progressList = LitePal.where("topicsCode=?", topicsCode).find(SectionsProgressVo::class.java)
            if (progressList.isNotEmpty()) {
                val map = mutableMapOf<String, Int>()
                progressList.forEach {
                    map[it.sectionsCode] = it.progress
                }
                return map
            }
        }
        val username = App.getLoginSharedPreferences().getString(LoginService.USERNAME, "")
        val map1 = mutableMapOf("username" to username)
        val response = NetUtils.postForResponse(AppConsts.FIND_SECTIONS_PROGRESS, map1)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<List<SectionsProgressVo>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<SectionsProgressVo>>>() {})
        if (resultVo.success) {
            val list = resultVo.data!!
            LitePal.deleteAll(SectionsProgressVo::class.java, "topicsCode=?", topicsCode)
            list.forEach {
                it.topicsCode = topicsCode
            }
            LitePal.saveAll(list)
            val map = mutableMapOf<String, Int>()
            resultVo.data.forEach {
                map[it.sectionsCode] = it.progress
            }
            return map
        }
        throw BizException(resultVo)
    }

    override fun submitAnswers(examsCode: String, examsAnswers: List<ExamsAnswer>): ResultVo<SubmitAnswersResVo> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["examsCode"] = examsCode
            map["examsAnswers"] = examsAnswers
            response = NetUtils.postForResponse(AppConsts.SUBMIT_ANSWERS, map)
        } catch (e: LoginException) {
            return ResultVo(errorCode = e.errorCode, errorMsg = e.message)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(
                errorCode = AppConsts.ERROR_CODE,
                errorMsg = e.message ?: App.context.getString(R.string.network_error)
            )
        }
        val resJsonStr: String = response.body!!.string()
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<SubmitAnswersResVo>>() {})
    }

    private fun getPage(): Page {
        val page = Page()
        page.pageSize = 99999
        page.pageNum = 1
        return page
    }
}


