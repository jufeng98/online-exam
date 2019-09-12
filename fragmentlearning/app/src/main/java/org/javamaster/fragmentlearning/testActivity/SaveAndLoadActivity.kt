package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_save_and_load.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.io.FileInputStream
import java.io.PrintWriter
import java.util.*

/**
 * 文件固定存储到/data/data/packageName/files目录内
 * SharedPreferences把内容保存在/data/data/packageName/shared_prefs目录下的xml文件
 */
class SaveAndLoadActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_save_and_load
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            var inputStream: FileInputStream? = openFileInput("save_and_load") ?: return
            var reader = Scanner(inputStream)
            var str = StringBuilder()
            while (reader.hasNext()) {
                str.append(reader.next())
            }
            inputStream?.close()
            edit_text.setText(str.toString())
        } catch (e: Exception) {
            Log.i(this::class.qualifiedName, e.message)
        }
        var text1 = getSharedPreferences("save_and_load", Context.MODE_PRIVATE).getString("hello", "")
        edit_text_shared.setText(text1)
        var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        var text = edit_text.text.toString()
        // Context.MODE_PRIVATE覆盖已有内容 MODE_APPEND 追加
        var outputStream = openFileOutput("save_and_load", Context.MODE_PRIVATE)
        var writer = PrintWriter(outputStream)
        writer.write(text)
        writer.flush()
        outputStream.close()
        writer.close()

        var text1 = edit_text_shared.text.toString()
        var edit = getSharedPreferences("save_and_load", Context.MODE_PRIVATE).edit()
        edit.putString("hello", text1)
        edit.apply()
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, SaveAndLoadActivity::class.java)
            context.startActivity(intent)
        }
    }
}
