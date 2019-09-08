package org.javamaster.fragmentlearning.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_learn.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.LearnAdapter
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.TopicsService
import org.javamaster.fragmentlearning.data.model.Topics
import org.javamaster.fragmentlearning.enums.TopicsTypeEnum
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import javax.inject.Inject
import kotlin.concurrent.thread


class LearnFragment : Fragment() {
    @Inject
    lateinit var topicsService: TopicsService
    var topicsList: List<Topics>? = null
    private var adaptersMap: MutableMap<Int, LearnAdapter> = mutableMapOf()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_learn, container, false)
        ButterKnife.bind(this, view)
        var search = view.findViewById<SearchView>(R.id.search)
        search.clearFocus()
        search.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activity!!.findViewById<View>(R.id.include_tab).visibility = View.GONE
            } else {
                activity!!.findViewById<View>(R.id.include_tab).visibility = View.VISIBLE
            }
        }
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(activity, getString(R.string.app_completing), Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(activity, getString(R.string.app_completing), Toast.LENGTH_SHORT).show()
                return false
            }

        })
        return view
    }

    override fun onResume() {
        super.onResume()
        swipe_refresh.isRefreshing = true
        if (topicsList == null) {
            TopicsAsyncTask().execute()
        } else {
            initAdapter()
        }
        swipe_refresh.setOnRefreshListener {
            thread {
                topicsList = topicsService.findTopicsList().data
                activity!!.runOnUiThread {
                    swipe_refresh.isRefreshing = false
                    if (topicsList == null) {
                        Toast.makeText(this@LearnFragment.activity, getString(AppConsts.ERROR_MSG), Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        refresh()
                    }
                }
            }
        }
    }

    inner class TopicsAsyncTask : AsyncTask<Void, Int, List<Topics>?>() {
        override fun doInBackground(vararg params: Void?): List<Topics>? {
            return topicsService.findTopicsList().data
        }

        override fun onPostExecute(result: List<Topics>?) {
            if (result == null) {
                swipe_refresh.isRefreshing = false
                Toast.makeText(this@LearnFragment.activity, getString(AppConsts.ERROR_MSG), Toast.LENGTH_SHORT).show()
                return
            }
            topicsList = result
            initAdapter()
        }
    }

    fun initAdapter() {
        swipe_refresh.isRefreshing = false
        var map = topicsList!!.groupBy { it.topicsType }
        map.forEach {
            var linearLayout: LinearLayout = view!!.findViewById(R.id.linear_layout)
            var newView = LayoutInflater.from(activity).inflate(R.layout.fragment_learn_part, null, false)

            var view1: TextView = newView.findViewById(R.id.no_course_text)
            view1.text = getString(TopicsTypeEnum.getEnumByCode(it.key!!)!!.msg)
            var view2: TextView = newView.findViewById(R.id.no_course_manage)
            view2.text = getString(R.string.view_more)
            view2.setOnClickListener {
                Toast.makeText(activity, R.string.app_completing, Toast.LENGTH_SHORT).show()
            }
            var view3: RecyclerView = newView.findViewById(R.id.no_course_recycler_view)
            view3.layoutManager = getLayoutManage()
            var learnAdapter = LearnAdapter(it.value, true)
            adaptersMap[it.key] = learnAdapter
            view3.adapter = learnAdapter
            linearLayout.addView(newView)
        }
    }

    private fun refresh() {
        var map = topicsList!!.groupBy { it.topicsType }
        map.forEach {
            var adapter = adaptersMap[it.key]
            if (adapter != null) {
                adapter.topicsList = it.value
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun getLayoutManage(): LinearLayoutManager {
        var linearLayoutManager = LinearLayoutManager(this@LearnFragment.activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return linearLayoutManager
    }

    companion object {
        @JvmStatic
        fun newInstance() = LearnFragment()
    }
}
