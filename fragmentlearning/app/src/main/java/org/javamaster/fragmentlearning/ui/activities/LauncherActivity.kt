package org.javamaster.fragmentlearning.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebView

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
        var intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
