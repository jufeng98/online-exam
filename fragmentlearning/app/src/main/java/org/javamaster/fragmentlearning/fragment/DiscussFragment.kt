package org.javamaster.fragmentlearning.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.ButterKnife
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.DiscussAdapter
import org.javamaster.fragmentlearning.adapter.LoadMoreWrapper
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.model.Discussions
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.EndlessRecyclerOnScrollListener
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.DiscussService
import javax.inject.Inject
import kotlin.math.ceil

class DiscussFragment : MainFragment() {

    @Inject
    lateinit var discussService: DiscussService
    private var discussDisposable: Disposable? = null
    private var refreshDiscussDisposable: Disposable? = null
    private var loadMoreMessagesDisposable: Disposable? = null
    private lateinit var discussionsList: MutableList<Discussions>
    lateinit var loadMoreWrapper: LoadMoreWrapper
    var pageTotal: Long = 0
    val page = Page().apply {
        pageNum = 1
        pageSize = 5
    }

    private lateinit var search: SearchView
    private lateinit var spinner: Spinner
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_discuss, container, false)
        ButterKnife.bind(this, view)
        search = view.findViewById<View>(R.id.discuss_search) as SearchView
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
        spinner = view.findViewById<View>(R.id.discuss_spinner) as Spinner
        val spinnerAdapter =
            ArrayAdapter.createFromResource(context!!, R.array.discussion_filter_titles, R.layout.view_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(context!!, R.string.app_completing, Toast.LENGTH_SHORT).show()
            }
        }
        refresh = view.findViewById(R.id.discuss_swipe_refresh)
        recyclerView = view.findViewById(R.id.discuss_recycler_view)
        return view
    }

    override fun onResume() {
        super.onResume()
        refresh.isRefreshing = true
        discussDisposable = Observable.create<Pair<MutableList<Discussions>, Long>> {
            it.onNext(
                discussService.findDiscussionsList(search.query.toString(), spinner.selectedItemPosition, page)
            )
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            refresh.isRefreshing = false
            discussionsList = it.first
            pageTotal = ceil((1.0 * it.second / page.pageSize)).toLong()
            val adapter = DiscussAdapter(discussionsList)
            loadMoreWrapper = LoadMoreWrapper(adapter)
            recyclerView.adapter = loadMoreWrapper
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }, {
            refresh.isRefreshing = false
            OperationListener.fail(it)
        })
        refresh.setOnRefreshListener {
            page.pageNum = 1
            refreshDiscussDisposable = Observable.create<Pair<MutableList<Discussions>, Long>> {
                it.onNext(
                    discussService.findDiscussionsList(search.query.toString(), spinner.selectedItemPosition, page)
                )
                it.onComplete()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                refresh.isRefreshing = false
                discussionsList.clear()
                discussionsList.addAll(it.first)
                pageTotal = ceil((1.0 * it.second / page.pageSize)).toLong()
                loadMoreWrapper.notifyDataSetChanged()
            }, {
                refresh.isRefreshing = false
                OperationListener.fail(it)
            })
        }

        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADING)
                page.pageNum += 1
                if (page.pageNum > pageTotal) {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADING_END)
                    return
                }
                loadMoreMessagesDisposable = Observable.create<Pair<MutableList<Discussions>, Long>> {
                    val list =
                        discussService.findDiscussionsList(search.query.toString(), spinner.selectedItemPosition, page)
                    it.onNext(list)
                    it.onComplete()
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                    discussionsList.addAll(it.first)
                    pageTotal = ceil((1.0 * it.second / page.pageSize)).toLong()
                    loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADING_COMPLETE)
                }, {
                    OperationListener.fail(it)
                })
            }
        })
    }

    override fun onPause() {
        super.onPause()
        discussDisposable?.dispose()
        refreshDiscussDisposable?.dispose()
        loadMoreMessagesDisposable?.dispose()
    }

    companion object {
        @JvmStatic
        fun newInstance() = DiscussFragment()
    }
}
