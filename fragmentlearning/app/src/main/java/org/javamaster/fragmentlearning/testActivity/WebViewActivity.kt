package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class WebViewActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_web_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = WebViewClient()
        web_view.loadUrl("https://www.baidu.com")
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, WebViewActivity::class.java)
            context.startActivity(intent)
        }
    }
}
