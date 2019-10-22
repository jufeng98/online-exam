package org.javamaster.fragmentlearning.service.impl

import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Response
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.entity.Messages
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.exception.BizException
import org.javamaster.fragmentlearning.exception.LoginException
import org.javamaster.fragmentlearning.service.MessagesService
import org.javamaster.fragmentlearning.utils.NetUtils

/**
 * @author yudong
 * @date 2019/9/20
 */
class MessagesServiceImpl constructor(private val objectMapper: ObjectMapper) : MessagesService {

    override fun findMessagesList(page: Page): Pair<MutableList<Messages>, Long> {
        val map = mutableMapOf<String, Any>()
        map["page"] = page
        val response = NetUtils.postForResponse(AppConsts.FIND_MESSAGES_LIST, map)
        val resJsonStr: String = response.body!!.string()
        val resultVo: ResultVo<MutableList<Messages>> =
            objectMapper.readValue(resJsonStr, object : TypeReference<ResultVo<List<Messages>>>() {})
        if (resultVo.success) {
            return Pair(resultVo.data!!, resultVo.total!!)
        }
        throw BizException(resultVo)
    }

    override fun markMessages(id: Int?): ResultVo<Int> {
        val response: Response
        try {
            val map = mutableMapOf<String, Any>()
            if (id != null) {
                map["id"] = id
            }
            response = NetUtils.postForResponse(AppConsts.MARK_MESSAGES, map)
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

}