package org.javamaster.fragmentlearning.testActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class WebViewActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_web_view
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webSetting = web_view.settings
        WebView.setWebContentsDebuggingEnabled(true)
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        webSetting.setSupportZoom(false)
        webSetting.builtInZoomControls = false
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        webSetting.setAppCacheEnabled(true)
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.cacheMode = WebSettings.LOAD_DEFAULT
        webSetting.defaultTextEncodingName = "UTF-8"
        webSetting.allowUniversalAccessFromFileURLs = true
        webSetting.loadWithOverviewMode = true
        webSetting.useWideViewPort = false
        webSetting.textZoom = 100
        web_view.isClickable = true
        web_view.requestFocus(View.FOCUS_DOWN)
        web_view.setOnLongClickListener { true }
        web_view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> if (!v.hasFocus()) {
                    v.requestFocus()
                }
            }
            false
        }
        web_view.webViewClient = WebViewClient()
        web_view.webChromeClient = WebChromeClient()
        web_view.loadUrl("http://m.baidu.com")
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, WebViewActivity::class.java)
            context.startActivity(intent)
        }
    }
}
