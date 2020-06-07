package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.core.content.edit
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_knowledges.*
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.KnowledgesAdapter
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.Knowledges
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/9/13
 */
class KnowledgesActivity : BaseAppActivity() {

    @Inject
    lateinit var learnService: LearnService
    private lateinit var sectionsCode: String
    private lateinit var sectionsName: String
    private lateinit var knowledgesList: MutableList<Knowledges>
    private lateinit var questionNumVosMap: MutableMap<String, Int>
    private lateinit var knowledgesDisposable: Disposable
    private var refreshKnowledgesDisposable: Disposable? = null

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
        knowledgesDisposable = Observable.create<Pair<MutableList<Knowledges>, MutableMap<String, Int>>> {
            val first = learnService.findKnowledgesList(sectionsCode, true)
            val second = learnService.findKnowledgesQuestionNum(sectionsCode, true)
            it.onNext(Pair(first, second))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            initAdapter(it.first, it.second)
        }, {
            OperationListener.fail(it)
        })
        swipe_refresh.setOnRefreshListener {
            refreshKnowledgesDisposable =
                Observable.create<Pair<MutableList<Knowledges>, MutableMap<String, Int>>> {
                    val first = learnService.findKnowledgesList(sectionsCode, false)
                    val second = learnService.findKnowledgesQuestionNum(sectionsCode, false)
                    it.onNext(Pair(first, second))
                    it.onComplete()
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                    swipe_refresh.isRefreshing = false
                    knowledgesList.clear()
                    knowledgesList.addAll(it.first)
                    questionNumVosMap.clear()
                    questionNumVosMap.putAll(it.second)
                    knowledges_recycler_view.adapter!!.notifyDataSetChanged()
                }, {
                    swipe_refresh.isRefreshing = false
                    OperationListener.fail(it)
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        knowledgesDisposable.dispose()
        refreshKnowledgesDisposable?.dispose()
    }

    private fun initAdapter(knowledges: MutableList<Knowledges>, questionNumVos: MutableMap<String, Int>) {
        this.knowledgesList = knowledges
        this.questionNumVosMap = questionNumVos
        val adapter = KnowledgesAdapter(this.knowledgesList, this.questionNumVosMap)
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
            App.getLearnSharedPreferences().edit {
                putString("sectionsCode", sectionsCode)
                apply()
            }
            context.startActivity(intent)
        }
    }

}
