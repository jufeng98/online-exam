package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_messages.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.LoadMoreWrapper
import org.javamaster.fragmentlearning.adapter.MessagesAdapter
import org.javamaster.fragmentlearning.asyncTask.MarkMessagesTask
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.Messages
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.EndlessRecyclerOnScrollListener
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.MessagesService
import javax.inject.Inject
import kotlin.math.ceil

/**
 * @author yudong
 * @date 2019/9/20
 */
class MessagesActivity : BaseAppActivity() {
    @Inject
    lateinit var messagesService: MessagesService
    lateinit var adapter: MessagesAdapter
    lateinit var loadMoreWrapper: LoadMoreWrapper
    var messagesList: MutableList<Messages> = mutableListOf()
    var pageTotal: Long = 0
    val page = Page().apply {
        pageNum = 1
        pageSize = 10
    }
    private lateinit var messagesDisposable: Disposable
    private var refreshMessagesDisposable: Disposable? = null
    private var loadMoreMessagesDisposable: Disposable? = null

    override fun initContentView(): Int? {
        return R.layout.activity_messages
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        supportActionBar?.title = getString(R.string.notifications)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        messages_swipe_refresh.isRefreshing = true
        messagesDisposable = Observable.create<Pair<List<Messages>, Long>> {
            val list = messagesService.findMessagesList(page)
            it.onNext(list)
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            messages_swipe_refresh.isRefreshing = false
            initAdapter(it.first)
            pageTotal = ceil((1.0 * it.second / page.pageSize)).toLong()
        }, {
            messages_swipe_refresh.isRefreshing = false
            OperationListener.fail(it)
        })

        messages_swipe_refresh.setOnRefreshListener {
            page.pageNum = 1
            refreshMessagesDisposable = Observable.create<Pair<List<Messages>, Long>> {
                val list = messagesService.findMessagesList(page)
                it.onNext(list)
                it.onComplete()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                messages_swipe_refresh.isRefreshing = false
                messagesList.clear()
                messagesList.addAll(it.first)
                loadMoreWrapper.notifyDataSetChanged()
                pageTotal = ceil((1.0 * it.second / page.pageSize)).toLong()
            }, {
                messages_swipe_refresh.isRefreshing = false
                OperationListener.fail(it)
            })
        }
        messages_recycler_view.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADING)
                page.pageNum += 1
                if (page.pageNum > pageTotal) {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADING_END)
                    return
                }
                loadMoreMessagesDisposable = Observable.create<Pair<List<Messages>, Long>> {
                    val list = messagesService.findMessagesList(page)
                    it.onNext(list)
                    it.onComplete()
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                    messagesList.addAll(it.first)
                    pageTotal = ceil((1.0 * it.second / page.pageSize)).toLong()
                    loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADING_COMPLETE)
                }, {
                    OperationListener.fail(it)
                })
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        messagesDisposable.dispose()
        refreshMessagesDisposable?.dispose()
        loadMoreMessagesDisposable?.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.mark_all_as_read))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                // 所有消息标为已读
                MarkMessagesTask(messagesService, object : OperationListener<Int> {
                    override fun success(t: Int) {
                        adapter.markAllAsRead()
                    }
                }).execute(null)
            }
            android.R.id.home -> {
                super.onKeyDown(KeyEvent.KEYCODE_BACK, KeyEvent(KeyEvent.KEYCODE_BACK, 0))
                finish()
            }
        }
        return true
    }

    private fun initAdapter(messagesList: List<Messages>) {
        this.messagesList.addAll(messagesList)
        adapter = MessagesAdapter(this.messagesList, messagesService)
        loadMoreWrapper = LoadMoreWrapper(adapter)
        messages_recycler_view.adapter = loadMoreWrapper
        messages_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MessagesActivity::class.java)
            context.startActivity(intent)
        }
    }

}
