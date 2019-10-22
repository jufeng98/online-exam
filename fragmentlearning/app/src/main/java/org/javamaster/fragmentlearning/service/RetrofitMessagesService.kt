package org.javamaster.fragmentlearning.service

import io.reactivex.Observable
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.model.ResultVo
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author yudong
 * @date 2019/10/22
 */
interface RetrofitMessagesService {
    @POST(AppConsts.HAS_UNREAD_MESSAGES)
    fun hasUnreadMessages(@Body map: MutableMap<String, Any>): Observable<ResultVo<Boolean>>
}