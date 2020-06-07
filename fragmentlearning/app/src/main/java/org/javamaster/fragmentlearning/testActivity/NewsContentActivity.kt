package org.javamaster.fragmentlearning.testActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_news_content.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.News

class NewsContentActivity : AppCompatActivity() {
    private lateinit var news: News
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)
        news = intent.getSerializableExtra("news") as News
        news_title.text = news.title
        news_content.text = news.content
    }

    companion object {
        fun actionStart(context: Context, news: News) {
            val intent = Intent(context, NewsContentActivity::class.java)
            intent.putExtra("news", news)
            context.startActivity(intent)
        }
    }
}
