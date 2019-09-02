package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_display_message.*
import org.javamaster.fragmentlearning.R

/**
 * @author yudong
 * @date 2019/8/20
 */
class DisplayMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)
        val message = intent.getStringExtra(SendActivity::class.java.name + EXTRA_MESSAGE)
        textView2.text = message
    }
}
