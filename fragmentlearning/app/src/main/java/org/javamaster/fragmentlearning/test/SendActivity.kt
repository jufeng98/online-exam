package org.javamaster.fragmentlearning.test

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_send.*
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity


/**
 * @author yudong
 * @date 2019/8/20
 */
class SendActivity : BaseAppActivity() {

    private var retainedFragment: RetainedFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.javamaster.fragmentlearning.R.layout.activity_send)
        ButterKnife.bind(this)
        var fm = supportFragmentManager
        retainedFragment = fm.findFragmentByTag("data") as RetainedFragment?
        // create the fragment and data the first time
        if (retainedFragment == null) {
            // add the fragment
            retainedFragment = RetainedFragment()
            fm.beginTransaction().add(retainedFragment!!, "data").commitNow()
            // load the data from the web
            retainedFragment!!.data = mutableListOf("hello", "world")
        }
        Log.i(this::class.java.name, retainedFragment!!.toString())
    }

    public override fun onDestroy() {
        super.onDestroy()
        // store the data in the fragment
        retainedFragment!!.data = mutableListOf("data from web")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(this::class.java.name, savedInstanceState?.getString("content"))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("content", editText.text.toString())
    }

    @OnClick(org.javamaster.fragmentlearning.R.id.button)
    fun sendMessage(view: View) {
        var message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(SendActivity::class.java.name + EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

}
