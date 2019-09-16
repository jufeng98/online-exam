package org.javamaster.fragmentlearning.ui.activities

import android.os.Bundle
import org.javamaster.fragmentlearning.BuildConfig
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.service.LoginService.Companion.REMEMBER_ME_COOKIE_KEY
import org.javamaster.fragmentlearning.testActivity.ExerciseMainActivity

/**
 * 启动页
 * @author yudong
 * @date 2019/8/18
 */
class LauncherActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.EXERCISE_MODE) {
            ExerciseMainActivity.actionStart(this)
            finish()
            return
        }
        val preferences = App.getLoginSharedPreferences()
        val rememberMeCookie = preferences.getString(REMEMBER_ME_COOKIE_KEY, "")
        if (rememberMeCookie == "") {
            OnboardingActivity.actionStart(this)
            finish()
            return
        }
        MainActivity.actionStart(this)
        finish()
    }
}
