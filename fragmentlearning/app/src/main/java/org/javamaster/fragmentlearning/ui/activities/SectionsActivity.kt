package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_sections.*
import kotlinx.android.synthetic.main.fragment_learn.swipe_refresh
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.SectionsAdapter
import org.javamaster.fragmentlearning.asyncTask.SectionsAsyncTask
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.entity.Sections
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import org.litepal.LitePal
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/9/12
 */
class SectionsActivity : BaseAppActivity() {

    @Inject
    lateinit var learnService: LearnService

    override fun initContentView(): Int? {
        return R.layout.activity_sections
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        app_tool_bar.title = intent.getStringExtra("topicsName")
        setSupportActionBar(app_tool_bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val listener = object : OperationListener<List<Sections>> {
            override fun success(t: List<Sections>) {
                swipe_refresh.isRefreshing = false
                LitePal.deleteAll(Sections::class.java)
                // 缓存到数据库
                LitePal.saveAll(t)
                initAdapter(t)
            }

            override fun fail(errorCode: Int, errorMsg: String) {
                swipe_refresh.isRefreshing = false
                if (errorCode != AppConsts.LOGIN_ERROR_CODE) {
                    Toast.makeText(this@SectionsActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        }
        swipe_refresh.isRefreshing = true
        val sectionsList = LitePal.findAll(Sections::class.java)
        if (sectionsList.isNotEmpty()) {
            initAdapter(sectionsList)
            swipe_refresh.isRefreshing = false
        } else {
            SectionsAsyncTask(learnService, listener).execute(intent.getStringExtra("topicsCode"))
        }
        swipe_refresh.setOnRefreshListener {
            SectionsAsyncTask(learnService, listener).execute(intent.getStringExtra("topicsCode"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        app_tool_bar.inflateMenu(R.menu.menu_session)
        return true
    }

    private fun initAdapter(sectionsList: List<Sections>) {
        val adapter = SectionsAdapter(sectionsList)
        val layoutManager = GridLayoutManager(this, 2)
        sections_recycler_view.layoutManager = layoutManager
        sections_recycler_view.adapter = adapter
    }

    companion object {
        fun actionStart(context: Context, topicsCode: String, topicsName: String) {
            val intent = Intent(context, SectionsActivity::class.java)
            intent.putExtra("topicsCode", topicsCode)
            intent.putExtra("topicsName", topicsName)
            context.startActivity(intent)
        }
    }

}
