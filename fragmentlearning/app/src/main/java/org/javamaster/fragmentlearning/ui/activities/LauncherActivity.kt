package org.javamaster.fragmentlearning.ui.activities

import android.os.Bundle
import android.preference.PreferenceManager
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts

/**
 * @author yudong
 * @date 2019/8/18
 */
class LauncherActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_launcher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        var registerFlag = preferences.getString(AppConsts.REGISTER_FLAG, "")
        if (registerFlag == "") {
            OnboardingActivity.actionStart(this)
            finish()
            return
        }
        var rememberMeCookie = preferences.getString(AppConsts.REMEMBER_ME_COOKIE_KEY, "")
        if (rememberMeCookie == "") {
            LoginActivity.actionStart(this)
            finish()
            return
        }
        MainActivity.actionStart(this)
        finish()
    }
}
