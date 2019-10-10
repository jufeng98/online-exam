package org.javamaster.fragmentlearning.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_learn.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.LearnAdapter
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.Topics
import org.javamaster.fragmentlearning.enums.TopicsTypeEnum
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/8/18
 */
class LearnFragment : MainFragment() {
    @Inject
    lateinit var learnService: LearnService
    private lateinit var topicsDisposable: Disposable
    private var refreshTopicsDisposable: Disposable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_learn, container, false)
        ButterKnife.bind(this, view)
        val search = view.findViewById<SearchView>(R.id.search)
        search.isIconified = true
        search.setIconifiedByDefault(false)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(activity, getString(R.string.app_completing), Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        topicsDisposable = Observable.create<Pair<MutableList<Topics>, Map<String, Int>>> {
            val first = learnService.findTopicsList(true)
            val second = learnService.findTopicsProgress(true)
            it.onNext(Pair(first, second))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            initAdapter(it)
        }, {
            OperationListener.fail(it)
        })
        swipe_refresh.setOnRefreshListener {
            refreshTopicsDisposable = Observable.create<Pair<MutableList<Topics>, Map<String, Int>>> {
                val first = learnService.findTopicsList(false)
                val second = learnService.findTopicsProgress(false)
                it.onNext(Pair(first, second))
                it.onComplete()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                swipe_refresh.isRefreshing = false
                initAdapter(it)
            }, {
                swipe_refresh.isRefreshing = false
                OperationListener.fail(it)
            })
        }
    }

    override fun onPause() {
        super.onPause()
        topicsDisposable.dispose()
        refreshTopicsDisposable?.dispose()
    }


    @SuppressLint("InflateParams")
    private fun initAdapter(pair: Pair<MutableList<Topics>, Map<String, Int>>) {
        val map = pair.first.groupBy { it.topicsType }
        val topicsPlaceholder: LinearLayout = view!!.findViewById(R.id.topics_placeholder)
        topicsPlaceholder.removeAllViews()
        map.forEach {
            val newView = LayoutInflater.from(activity).inflate(R.layout.fragment_learn_part, null, false)
            val topicsNameView: TextView = newView.findViewById(R.id.no_course_text)
            topicsNameView.text = getString(TopicsTypeEnum.getEnumByCode(it.key!!)!!.msg)
            val showMoreView: TextView = newView.findViewById(R.id.no_course_manage)
            showMoreView.text = getString(R.string.view_more)
            showMoreView.setOnClickListener {
                Toast.makeText(activity, R.string.app_completing, Toast.LENGTH_SHORT).show()
            }
            val topicsRecyclerView: RecyclerView = newView.findViewById(R.id.no_course_recycler_view)
            topicsRecyclerView.layoutManager = getLayoutManage()
            val learnAdapter = LearnAdapter(it.value, pair.second, true)
            topicsRecyclerView.adapter = learnAdapter
            topicsPlaceholder.addView(newView)
        }
    }

    private fun getLayoutManage(): LinearLayoutManager {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return linearLayoutManager
    }

    companion object {
        @JvmStatic
        fun newInstance() = LearnFragment()
    }
}
