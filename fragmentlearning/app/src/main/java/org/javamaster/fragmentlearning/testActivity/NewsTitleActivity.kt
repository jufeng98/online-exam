package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.*
import org.apache.commons.lang3.RandomStringUtils
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.News
import org.javamaster.fragmentlearning.testAdapter.NewsTitleAdapter

class NewsTitleActivity : AppCompatActivity() {
    private lateinit var newsList: MutableList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_title)
        initList()
        var recyclerView: RecyclerView = recycler_view as RecyclerView
        var linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        var adapter = NewsTitleAdapter(newsList)
        recyclerView.adapter = adapter
    }

    private fun initList() {
        newsList = mutableListOf()
        for (i in 1..100) {
            var new =
                News("$i:${RandomStringUtils.randomAlphabetic(20)}", "$i:${RandomStringUtils.randomAlphabetic(1000)}")
            newsList.add(new)
        }
    }
}
