package org.javamaster.fragmentlearning.testFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_news_content.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.News

class NewsContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_news_content, container, false)
    }

    fun refresh(news: News) {
        news_title.text = news.title
        news_content.text = news.content
    }

}
