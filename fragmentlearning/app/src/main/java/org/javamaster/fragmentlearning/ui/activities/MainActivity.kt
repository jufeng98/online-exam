package org.javamaster.fragmentlearning.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.javamaster.fragmentlearning.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
