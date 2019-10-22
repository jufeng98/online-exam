package org.javamaster.fragmentlearning

import io.reactivex.Observable
import org.javamaster.fragmentlearning.data.entity.Messages
import org.javamaster.fragmentlearning.data.model.ResultVo
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author yudong
 * @date 2019/10/22
 */
interface RetrofitService {
    @POST("core/messages/findMessagesList")
    fun findMessagesList(@Body map: MutableMap<String, Any>): Observable<ResultVo<List<Messages>>>
}