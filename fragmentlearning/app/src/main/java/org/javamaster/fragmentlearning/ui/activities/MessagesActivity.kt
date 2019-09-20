package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_messages.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.LoadMoreWrapper
import org.javamaster.fragmentlearning.adapter.MessagesAdapter
import org.javamaster.fragmentlearning.asyncTask.MarkMessagesTask
import org.javamaster.fragmentlearning.asyncTask.MessagesTask
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.Messages
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.EndlessRecyclerOnScrollListener
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.MessagesService
import javax.inject.Inject

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

    override fun initContentView(): Int? {
        return R.layout.activity_messages
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        supportActionBar?.title = getString(R.string.notifications)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val listener = object : OperationListener<Pair<List<Messages>, Long>> {
            override fun success(t: Pair<List<Messages>, Long>) {
                messages_swipe_refresh.isRefreshing = false
                initAdapter(t.first)
                pageTotal = Math.ceil((1.0 * t.second / page.pageSize)).toLong()
            }

            override fun fail(errorCode: Int, errorMsg: String) {
                messages_swipe_refresh.isRefreshing = false
                super.fail(errorCode, errorMsg)
            }
        }
        messages_swipe_refresh.isRefreshing = true
        MessagesTask(messagesService, listener).execute(page)
        messages_swipe_refresh.setOnRefreshListener {
            page.pageNum = 1
            MessagesTask(messagesService, object : OperationListener<Pair<List<Messages>, Long>> {
                override fun success(t: Pair<List<Messages>, Long>) {
                    messages_swipe_refresh.isRefreshing = false
                    messagesList.clear()
                    messagesList.addAll(t.first)
                    loadMoreWrapper.notifyDataSetChanged()
                    pageTotal = Math.ceil((1.0 * t.second / page.pageSize)).toLong()
                }

                override fun fail(errorCode: Int, errorMsg: String) {
                    messages_swipe_refresh.isRefreshing = false
                    super.fail(errorCode, errorMsg)
                }

            }).execute(page)
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
                MessagesTask(messagesService, object : OperationListener<Pair<List<Messages>, Long>> {
                    override fun success(t: Pair<List<Messages>, Long>) {
                        messagesList.addAll(t.first)
                        loadMoreWrapper.setLoadState(LoadMoreWrapper.LOADING_COMPLETE)
                    }
                }).execute(page)
            }
        })
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

    fun initAdapter(messagesList: List<Messages>) {
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
