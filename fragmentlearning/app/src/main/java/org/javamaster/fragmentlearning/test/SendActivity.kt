package org.javamaster.fragmentlearning.test

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_send.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class SendActivity : BaseAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        ButterKnife.bind(this)

    }

    @OnClick(R.id.button)
    fun sendMessage(view: View) {
        var message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(SendActivity::class.java.name + EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}
