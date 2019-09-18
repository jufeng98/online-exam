package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.content.edit
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_sections.*
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.SectionsAdapter
import org.javamaster.fragmentlearning.asyncTask.SectionsAsyncTask
import org.javamaster.fragmentlearning.common.App
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
        setSupportActionBar(app_tool_bar)
        supportActionBar!!.title = intent.getStringExtra("topicsName")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        val topicsCode = intent.getStringExtra("topicsCode")
        val listener = object : OperationListener<Pair<List<Sections>, Map<String, Int>>> {
            override fun success(t: Pair<List<Sections>, Map<String, Int>>) {
                swipe_refresh.isRefreshing = false
                LitePal.deleteAll(Sections::class.java, "topicsCode=?", topicsCode)
                // 缓存到数据库
                LitePal.saveAll(t.first)
                initAdapter(t.first, t.second)
            }

            override fun fail(errorCode: Int, errorMsg: String) {
                swipe_refresh.isRefreshing = false
                super.fail(errorCode, errorMsg)
            }
        }
        val sectionsList = LitePal.where("topicsCode=?", topicsCode).find(Sections::class.java)
        val map = LearnService.getSectionsProgressMap()
        if (sectionsList.isNotEmpty()) {
            initAdapter(sectionsList, map)
        } else {
            swipe_refresh.isRefreshing = true
            SectionsAsyncTask(learnService, listener).execute(topicsCode)
        }
        swipe_refresh.setOnRefreshListener {
            SectionsAsyncTask(learnService, listener).execute(topicsCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        app_tool_bar.inflateMenu(R.menu.menu_session)
        return true
    }

    private fun initAdapter(
        sectionsList: List<Sections>,
        map: Map<String, Int>
    ) {
        if (sectionsList.isEmpty()) {
            no_data.visibility = View.VISIBLE
            return
        }

        val adapter = SectionsAdapter(sectionsList, map)
        val layoutManager = GridLayoutManager(this, 2)
        sections_recycler_view.layoutManager = layoutManager
        sections_recycler_view.adapter = adapter
    }

    companion object {
        fun actionStart(context: Context, topicsCode: String, topicsName: String) {
            val intent = Intent(context, SectionsActivity::class.java)
            intent.putExtra("topicsCode", topicsCode)
            intent.putExtra("topicsName", topicsName)
            App.getLearnSharedPreferences().edit {
                putString("topicsCode", topicsCode)
                apply()
            }
            context.startActivity(intent)
        }
    }

}
