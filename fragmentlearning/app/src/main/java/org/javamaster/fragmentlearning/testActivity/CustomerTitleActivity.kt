package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.javamaster.fragmentlearning.R

class CustomerTitleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_title)
        var actionBar = actionBar
        actionBar?.hide()
    }
}
