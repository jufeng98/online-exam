package org.javamaster.fragmentlearning.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.javamaster.fragmentlearning.GlobalHandler
import org.javamaster.fragmentlearning.ioc.DaggerGlobalComponent
import org.javamaster.fragmentlearning.ioc.GlobalComponent
import org.litepal.LitePal

/**
 * @author yudong
 * @date 2019/8/18
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        app = this
        context = applicationContext
        globalComponent = DaggerGlobalComponent.create()
        GlobalHandler.getInstance().init(context)
        objectMapper = ObjectMapper()
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    }

    companion object {
        lateinit var app: App
        lateinit var context: Context
        lateinit var objectMapper: ObjectMapper
        lateinit var globalComponent: GlobalComponent
        val activities: MutableSet<Activity> = mutableSetOf()

        fun getLoginSharedPreferences(): SharedPreferences {
            return context.getSharedPreferences(
                context.packageName + "_login_user_info",
                Context.MODE_PRIVATE
            )
        }

        fun addActivity(activity: Activity) {
            activities.add(activity)
        }

        fun removeActivity(activity: Activity) {
            activities.remove(activity)
        }

        fun finishAll() {
            activities.forEach {
                if (!it.isFinishing) {
                    it.finish()
                }
            }
            activities.clear()
        }

        fun finishExcept(exceptActivityClasses: MutableSet<Class<out Activity>>) {
            activities.filter {
                for (exceptActivityClass in exceptActivityClasses) {
                    if (it.javaClass.name == exceptActivityClass.name) {
                        false
                    }
                }
                true
            }.forEach {
                if (!it.isFinishing) {
                    it.finish()
                }
                activities.remove(it)
            }
        }
    }

}