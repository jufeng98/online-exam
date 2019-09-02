package org.javamaster.fragmentlearning.common

import android.app.Activity
import android.app.Application
import android.content.Context
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
    }

    companion object {
        lateinit var app: App
        lateinit var context: Context
        lateinit var globalComponent: GlobalComponent
        val activities: MutableSet<Activity> = mutableSetOf()

        fun addActivity(activity: Activity) {
            activities.add(activity)
        }

        fun removeActivity(activity: Activity) {
            activities.remove(activity)
        }

        fun finishAll() {
            for (activity in activities) {
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
            activities.clear()
        }
    }

}