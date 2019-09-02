package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_http_connection.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class HttpConnectionActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_http_connection
    }

    @OnClick(R.id.send_request)
    fun send() {
        Thread {
            var http = URL("https://www.baidu.com").openConnection() as HttpsURLConnection
            http.requestMethod = "GET"
            http.connectTimeout = 3000
            http.readTimeout = 3000
            var scanner = Scanner(http.inputStream)
            var stringBuilder = StringBuilder()
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.next())
            }
            http.disconnect()
            runOnUiThread {
                text_view.text = stringBuilder.toString()
            }
        }.start()
    }

    companion object {
        fun actonStart(context: Activity) {
            var intent = Intent(context, HttpConnectionActivity::class.java)
            context.startActivity(intent)
        }
    }
}
