package org.javamaster.fragmentlearning.common

import android.app.Application
import android.content.Context
import org.javamaster.fragmentlearning.GlobalHandler

/**
 * @author yudong
 * @date 2019/8/18
 */
class App : Application() {

    var isInitialized: Boolean = false

    fun initializeFromCache(): Boolean {
        if (this.isInitialized) {
            return true
        }
        return false
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        GlobalHandler.getInstance().init(context)
    }

    companion object {
        var context: Context? = null
            private set
        var app: App? = null
    }

}