package org.javamaster.fragmentlearning

import android.util.Log
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.utils.NetUtils
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

@RunWith(AndroidJUnit4ClassRunner::class)
class RetrofitTest {

    @Test
    fun testGetMessages() {
        val retrofit = Retrofit.Builder()
            .client(NetUtils.getClient())
            .baseUrl(AppConsts.BASE_URL + AppConsts.APP_CONTEXT + "/")
            .addConverterFactory(JacksonConverterFactory.create(App.objectMapper))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val service = retrofit.create(RetrofitService::class.java)
        val map = mutableMapOf<String, Any>()
        map["page"] = Page().apply {
            pageNum = 1
            pageSize = 10
        }
        service.findMessagesList(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(this::class.qualifiedName, it.toString())
            }, {
                Log.e(this::class.qualifiedName, "error", it)
            })
    }

}
