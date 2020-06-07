package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.widget.Toast
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_meassage_handler.*
import org.apache.commons.lang3.RandomStringUtils
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MessageHandlerActivity : BaseAppActivity() {
    private var handler: Handler = Handler(Handler.Callback {
        when (it.what) {
            1 -> textView14.text = it.obj as CharSequence?
        }
        true
    })
    private var downloadAsyncTask: DownloadAsyncTask? = null

    override fun initContentView(): Int? {
        return R.layout.activity_meassage_handler
    }

    @OnClick(R.id.button4)
    fun requireMsgFromNetwork() {
        thread {
            TimeUnit.SECONDS.sleep(2)
            // 模拟从网络得到的数据
            val message = Message()
            message.what = 1
            message.obj = RandomStringUtils.randomAlphabetic(20)
            handler.sendMessage(message)
        }
    }

    @OnClick(R.id.button8)
    fun download() {
        if (downloadAsyncTask != null && downloadAsyncTask!!.status == AsyncTask.Status.RUNNING) {
            Toast.makeText(this, "任务已经在运行中......", Toast.LENGTH_SHORT).show()
            return
        }
        downloadAsyncTask = DownloadAsyncTask(
            { progress ->
                progress_bar.progress = progress
            },
            { result ->
                if (result) {
                    Toast.makeText(this, "下载成功", Toast.LENGTH_SHORT).show()
                }
            })
        downloadAsyncTask!!.execute()
    }

    companion object {
        fun actonStart(context: Activity) {
            val intent = Intent(context, MessageHandlerActivity::class.java)
            context.startActivity(intent)
        }
    }

    class DownloadAsyncTask(
        private var onProgressUpdate: (progress: Int) -> Unit,
        private var onPostExecute: (result: Boolean) -> Unit
    ) : AsyncTask<Void, Int, Boolean>() {
        var progress = 0
        override fun doInBackground(vararg params: Void?): Boolean {
            // 模拟下载进度
            while (progress <= 100) {
                TimeUnit.SECONDS.sleep(1)
                progress += 10
                publishProgress(progress)
            }
            return true
        }

        override fun onProgressUpdate(vararg values: Int?) {
            onProgressUpdate.invoke(values[0]!!)
        }

        override fun onPostExecute(result: Boolean?) {
            onPostExecute.invoke(result!!)
        }

    }
}
