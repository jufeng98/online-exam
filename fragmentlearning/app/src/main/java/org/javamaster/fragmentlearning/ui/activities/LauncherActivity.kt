package org.javamaster.fragmentlearning.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.webkit.WebView
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.test.SendActivity

/**
 * @author yudong
 * @date 2019/8/18
 */
class LauncherActivity : BaseAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= 24) {
            WebView(this)
        }
        super.onCreate(savedInstanceState)
//      TODO
        var preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        var rememberMeCookie = preferences.getString(AppConsts.REMEMBER_ME_COOKIE_KEY, "")
//        if (rememberMeCookie == "") {
        var intent = Intent(applicationContext, SendActivity::class.java)
        startActivity(intent)
//        } else {
//
//        }
        finish()
    }
}
