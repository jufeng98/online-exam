package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import org.javamaster.fragmentlearning.R

/**
 * @author yudong
 * @date 2019/9/13
 */
class KnowledgesActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_knowledges
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = intent.getStringExtra("sectionsName")
    }

    companion object {
        fun actionStart(context: Context, sectionsCode: String, sectionsName: String) {
            val intent = Intent(context, SectionsActivity::class.java)
            intent.putExtra("sectionsCode", sectionsCode)
            intent.putExtra("sectionsName", sectionsName)
            context.startActivity(intent)
        }
    }

}
