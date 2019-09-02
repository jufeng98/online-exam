package org.javamaster.fragmentlearning.testAdapter

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.News
import org.javamaster.fragmentlearning.testActivity.NewsContentActivity
import org.javamaster.fragmentlearning.testActivity.NewsTitleActivity
import org.javamaster.fragmentlearning.testFragment.NewsContentFragment


/**
 * @author yudong
 * @date 2019/8/25
 */
class NewsTitleAdapter(private val newsList: List<News>) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(org.javamaster.fragmentlearning.R.layout.news_item_layout, parent, false)
        var holder = NewsViewHolder(view)
        holder.view.setOnClickListener {
            var position = holder.adapterPosition
            var news = newsList[position]

            val mConfiguration = parent.context.resources.configuration
            val ori = mConfiguration.orientation
            if (ori == Configuration.ORIENTATION_LANDSCAPE) {
                //横屏
                var activity = parent.context as NewsTitleActivity
                var fragment =
                    activity.supportFragmentManager.findFragmentById(R.id.news_content_fragment) as NewsContentFragment
                fragment.refresh(news)
            } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏
                NewsContentActivity.actionStart(parent.context, news)
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var news = newsList[position]
        holder.newsTitle.text = news.title
    }

}

data class NewsViewHolder(val view: View) :
    RecyclerView.ViewHolder(view) {
    val newsTitle: TextView = view.findViewById(org.javamaster.fragmentlearning.R.id.news_title)
}