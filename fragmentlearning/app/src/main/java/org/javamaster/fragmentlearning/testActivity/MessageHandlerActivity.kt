package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.widget.Toast
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_meassage_handler.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.util.concurrent.TimeUnit

class MessageHandlerActivity : BaseAppActivity() {
    var change = false
    var text = listOf("hello", "world")
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                1 -> {
                    textView14.text = msg.obj as CharSequence?
                }
            }
        }
    }

    override fun initContentView(): Int? {
        return R.layout.activity_meassage_handler
    }

    @OnClick(R.id.button4)
    fun update() {
        var message = Message()
        message.what = 1
        change = !change
        if (change) {
            message.obj = text[0]
        } else {
            message.obj = text[1]
        }
        handler.sendMessage(message)
    }

    @OnClick(R.id.button8)
    fun download() {
        var progress = 0
        object : AsyncTask<Void, Int, Boolean>() {
            override fun doInBackground(vararg params: Void?): Boolean {
                while (progress <= 100) {
                    TimeUnit.SECONDS.sleep(1)
                    progress += 10
                    publishProgress(progress)
                }
                return true
            }

            override fun onProgressUpdate(vararg values: Int?) {
                progressBar.progress = values[0]!!
            }

            override fun onPostExecute(result: Boolean?) {
                if (result!!) {
                    Toast.makeText(this@MessageHandlerActivity, "download finished", Toast.LENGTH_SHORT).show()
                }
            }
        }.execute()
    }

    companion object {
        fun actonStart(context: Activity) {
            var intent = Intent(context, MessageHandlerActivity::class.java)
            context.startActivity(intent)
        }
    }
}
