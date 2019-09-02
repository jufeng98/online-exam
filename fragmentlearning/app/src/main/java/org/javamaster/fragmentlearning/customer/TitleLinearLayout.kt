package org.javamaster.fragmentlearning.customer

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.linear_layout_menu.view.*
import org.javamaster.fragmentlearning.R

/**
 * @author yudong
 * @date 2019/8/24
 */
class TitleLinearLayout(context: Context, attributes: AttributeSet) : LinearLayout(context, attributes) {
    init {
        LayoutInflater.from(context).inflate(R.layout.linear_layout_menu, this)
        back.setOnClickListener {
            var activity = context as Activity
            activity.finish()
        }
        edit.setOnClickListener {
            Toast.makeText(context, "you click the edit button", Toast.LENGTH_LONG).show()
        }
    }
}