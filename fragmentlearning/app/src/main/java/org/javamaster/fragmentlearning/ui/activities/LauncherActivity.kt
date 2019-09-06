package org.javamaster.fragmentlearning.ui.activities

import android.os.Bundle
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.LoginService.Companion.REMEMBER_ME_COOKIE_KEY

/**
 * 启动页
 * @author yudong
 * @date 2019/8/18
 */
class LauncherActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_launcher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var preferences = App.getLoginSharedPreferences()
        var rememberMeCookie = preferences.getString(REMEMBER_ME_COOKIE_KEY, "")
        if (rememberMeCookie == "") {
            OnboardingActivity.actionStart(this)
            finish()
            return
        }
        MainActivity.actionStart(this)
        finish()
    }
}
