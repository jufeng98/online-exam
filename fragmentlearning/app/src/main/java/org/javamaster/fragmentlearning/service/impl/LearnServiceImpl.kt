package org.javamaster.fragmentlearning.service.impl

import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Response
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.entity.Knowledges
import org.javamaster.fragmentlearning.data.entity.Sections
import org.javamaster.fragmentlearning.data.entity.Topics
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.exception.LoginException
import org.javamaster.fragmentlearning.service.LearnService
import org.javamaster.fragmentlearning.utils.NetUtils

/**
 * @author yudong
 * @date 2019/8/18
 */
class LearnServiceImpl constructor(private val objectMapper: ObjectMapper) : LearnService {

    override fun findTopicsList(): ResultVo<List<Topics>> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["topicsForm"] = mapOf<String, Any>()
            map["page"] = getPage()
            response = NetUtils.postForResponse(AppConsts.FIND_TOPICS_LIST, map)
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
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Topics>>>() {})
    }

    override fun findSectionsList(topicsCode: String): ResultVo<List<Sections>> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["sectionsForm"] = mapOf<String, Any>("topicsCode" to topicsCode)
            map["page"] = getPage()
            response = NetUtils.postForResponse(AppConsts.FIND_SECTIONS_LIST, map)
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
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Sections>>>() {})
    }

    override fun findKnowledgesList(sectionsCode: String): ResultVo<List<Knowledges>> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            map["knowledgesForm"] = mapOf<String, Any>("sectionsCode" to sectionsCode)
            map["page"] = getPage()
            response = NetUtils.postForResponse(AppConsts.FIND_KNOWLEDGES_LIST, map)
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
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Knowledges>>>() {})
    }

    override fun findKnowledgesQuestionNum(): ResultVo<Map<String, Int>> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            response = NetUtils.postForResponse(AppConsts.FIND_KNOWLEDGES_QUESTION_NUM, map)
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
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<Map<String, Int>>>() {})
    }

    private fun getPage(): Page {
        val page = Page()
        page.pageSize = 99999
        page.pageNum = 1
        return page
    }
}


