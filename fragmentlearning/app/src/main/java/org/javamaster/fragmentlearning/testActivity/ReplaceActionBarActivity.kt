package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.javamaster.fragmentlearning.R

class ReplaceActionBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_title)
        val actionBar = actionBar
        actionBar?.hide()
    }
}
