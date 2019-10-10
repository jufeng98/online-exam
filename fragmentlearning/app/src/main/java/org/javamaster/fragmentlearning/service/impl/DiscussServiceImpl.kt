package org.javamaster.fragmentlearning.service.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Response
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.model.Discussions
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.exception.BizException
import org.javamaster.fragmentlearning.service.DiscussService
import org.javamaster.fragmentlearning.utils.NetUtils

/**
 * @author yudong
 * @date 2019/10/10
 */
class DiscussServiceImpl constructor(private val objectMapper: ObjectMapper) : DiscussService {

    override fun createDiscussions(question: String, description: String, relevantTags: String): Int {
        val response: Response
        val list = relevantTags.trim().split(Regex("\\s+"))
        val map = mutableMapOf("question" to question, "description" to description, "relevantTags" to list)
        response = NetUtils.postForResponse(AppConsts.CREATE_DISCUSSIONS, map)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<Int> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<Int>>() {})
        if (resultVo.success) {
            return resultVo.data!!
        }
        throw BizException(resultVo)
    }

    override fun findDiscussionsList(question: String, sort: Int, page: Page): Pair<MutableList<Discussions>, Long> {
        val response: Response
        val map = mutableMapOf("question" to question, "sort" to sort, "page" to page)
        response = NetUtils.postForResponse(AppConsts.FIND_DISCUSSIONS_LIST, map)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<MutableList<Discussions>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<MutableList<Discussions>>>() {})
        if (resultVo.success) {
            return Pair(resultVo.data!!, resultVo.total!!)
        }
        throw BizException(resultVo)
    }

}


