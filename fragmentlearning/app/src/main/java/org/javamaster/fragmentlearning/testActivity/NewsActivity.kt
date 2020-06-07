package org.javamaster.fragmentlearning.testActivity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.*
import org.apache.commons.lang3.RandomStringUtils
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.News
import org.javamaster.fragmentlearning.testAdapter.NewsTitleAdapter

class NewsActivity : AppCompatActivity() {
    private lateinit var newsList: MutableList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_title)
        initList()
        val recyclerView: RecyclerView = recycler_view_vertical as RecyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = NewsTitleAdapter(newsList)
        recyclerView.adapter = adapter
    }

    private fun initList() {
        newsList = mutableListOf()
        for (i in 1..100) {
            val new =
                News(
                    "$i:新闻标题${RandomStringUtils.randomAlphabetic(20)}",
                    "$i:新闻内容${RandomStringUtils.randomAlphabetic(1000)}"
                )
            newsList.add(new)
        }
    }
}
