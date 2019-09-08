package org.javamaster.fragmentlearning.data.impl

import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Response
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.TopicsService
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.Topics
import org.javamaster.fragmentlearning.utils.NetUtils

/**
 * @author yudong
 * @date 2019/8/18
 */
class TopicsServiceImpl constructor(private val objectMapper: ObjectMapper) : TopicsService {

    override fun findTopicsList(): ResultVo<List<Topics>> {
        var response: Response
        try {
            var map = mutableMapOf<String, Any>()
            map["topicsForm"] = mapOf<String, Any>()
            var page = Page()
            page.pageSize = 99999
            page.pageNum = 1
            map["page"] = page
            response = NetUtils.postForResponse(AppConsts.FIND_TOPICS_LIST, map)
        } catch (e: Exception) {
            Log.e(this::class.qualifiedName, "", e)
            return ResultVo(errorMsg = e.message ?: App.context.getString(R.string.network_error))
        }
        var resJsonStr: String = response.body!!.string()
        return objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Topics>>>() {})
    }

}


