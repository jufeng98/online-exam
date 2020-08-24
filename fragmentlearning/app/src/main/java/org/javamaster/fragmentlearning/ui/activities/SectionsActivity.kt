package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.content.edit
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sections.*
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.SectionsAdapter
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.Sections
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/9/12
 */
class SectionsActivity : BaseAppActivity() {

    @Inject
    lateinit var learnService: LearnService
    private lateinit var topicsCode: String
    private lateinit var progressMap: MutableMap<String, Int>
    private lateinit var sectionsList: MutableList<Sections>
    private lateinit var sectionsDisposable: Disposable
    private var refreshSectionsDisposable: Disposable? = null

    override fun initContentView(): Int? {
        return R.layout.activity_sections
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        setSupportActionBar(app_tool_bar)
        topicsCode = intent.getStringExtra("topicsCode")!!
        supportActionBar!!.title = intent.getStringExtra("topicsName")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        sectionsDisposable = Observable.create<Pair<MutableList<Sections>, MutableMap<String, Int>>> {
            val first = learnService.findSectionsList(topicsCode, true)
            val second = learnService.findSectionsProgress(topicsCode, true)
            it.onNext(Pair(first, second))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            initAdapter(it.first, it.second)
        }, {
            OperationListener.fail(it)
        })
        swipe_refresh.setOnRefreshListener {
            refreshSectionsDisposable = Observable.create<Pair<MutableList<Sections>, Map<String, Int>>> {
                val first = learnService.findSectionsList(topicsCode, false)
                val second = learnService.findSectionsProgress(topicsCode, false)
                it.onNext(Pair(first, second))
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                swipe_refresh.isRefreshing = false
                sectionsList.clear()
                sectionsList.addAll(it.first)
                progressMap.clear()
                progressMap.putAll(it.second)
                sections_recycler_view.adapter!!.notifyDataSetChanged()
            }, {
                swipe_refresh.isRefreshing = false
                OperationListener.fail(it)
            })
        }
    }

    override fun onPause() {
        super.onPause()
        sectionsDisposable.dispose()
        refreshSectionsDisposable?.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        app_tool_bar.inflateMenu(R.menu.menu_session)
        return true
    }

    private fun initAdapter(
        sectionsList: MutableList<Sections>,
        map: MutableMap<String, Int>
    ) {
        this.sectionsList = sectionsList
        this.progressMap = map
        if (this.sectionsList.isEmpty()) {
            no_data.visibility = View.VISIBLE
            return
        }
        val adapter = SectionsAdapter(this.sectionsList, this.progressMap)
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
