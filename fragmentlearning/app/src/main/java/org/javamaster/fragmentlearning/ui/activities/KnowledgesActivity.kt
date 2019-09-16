package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_knowledges.*
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.KnowledgesAdapter
import org.javamaster.fragmentlearning.asyncTask.KnowledgesAsyncTask
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.Knowledges
import org.javamaster.fragmentlearning.data.entity.KnowledgesQuestionNumVo
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import org.litepal.LitePal
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/9/13
 */
class KnowledgesActivity : BaseAppActivity() {

    @Inject
    lateinit var learnService: LearnService
    lateinit var sectionsCode: String
    private lateinit var sectionsName: String

    override fun initContentView(): Int? {
        return R.layout.activity_knowledges
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        sectionsName = intent.getStringExtra("sectionsName")
        sectionsCode = intent.getStringExtra("sectionsCode")
        setSupportActionBar(app_tool_bar)
        supportActionBar?.title = sectionsName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val listener = object : OperationListener<Pair<List<Knowledges>, List<KnowledgesQuestionNumVo>>> {
            override fun success(t: Pair<List<Knowledges>, List<KnowledgesQuestionNumVo>>) {
                swipe_refresh.isRefreshing = false
                LitePal.deleteAll(Knowledges::class.java, "sectionsCode=?", sectionsCode)
                LitePal.saveAll(t.first)
                LitePal.deleteAll(KnowledgesQuestionNumVo::class.java, "sectionsCode=?", sectionsCode)
                LitePal.saveAll(t.second)
                initAdapter(t.first, t.second)
            }

            override fun fail(errorCode: Int, errorMsg: String) {
                swipe_refresh.isRefreshing = false
                super.fail(errorCode, errorMsg)
            }
        }
        val knowledgesList = LitePal.where("sectionsCode=?", sectionsCode).find(Knowledges::class.java)
        val questionNumVos = LitePal.where("sectionsCode=?", sectionsCode).find(KnowledgesQuestionNumVo::class.java)
        if (knowledgesList.isNotEmpty()) {
            initAdapter(knowledgesList, questionNumVos)
        } else {
            swipe_refresh.isRefreshing = true
            KnowledgesAsyncTask(learnService, listener).execute(sectionsCode)
        }
        swipe_refresh.setOnRefreshListener {
            KnowledgesAsyncTask(learnService, listener).execute(sectionsCode)
        }
    }

    fun initAdapter(knowledges: List<Knowledges>, questionNumVos: List<KnowledgesQuestionNumVo>) {
        val adapter = KnowledgesAdapter(knowledges, questionNumVos)
        val layoutManager = GridLayoutManager(this, 2)
        knowledges_recycler_view.layoutManager = layoutManager
        knowledges_recycler_view.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                super.onKeyDown(KeyEvent.KEYCODE_BACK, KeyEvent(KeyEvent.KEYCODE_BACK, 0))
                finish()
            }
        }
        return true
    }

    companion object {
        fun actionStart(context: Context, sectionsCode: String, sectionsName: String) {
            val intent = Intent(context, KnowledgesActivity::class.java)
            intent.putExtra("sectionsCode", sectionsCode)
            intent.putExtra("sectionsName", sectionsName)
            context.startActivity(intent)
        }
    }

}
