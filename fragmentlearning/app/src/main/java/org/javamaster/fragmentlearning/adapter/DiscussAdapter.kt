package org.javamaster.fragmentlearning.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.lang3.RandomUtils
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.Discussions
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author yudong
 * @date 2019/10/10
 */
@Suppress("DEPRECATION")
class DiscussAdapter(
    private var discussionsList: List<Discussions>
) : RecyclerView.Adapter<DiscussAdapter.ViewHolder>() {
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_discussions_view_page, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return discussionsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val discussions = discussionsList[position]
        holder.apply {
            question.text = discussions.question
            val date = SimpleDateFormat("yyyy/MM/dd", Locale.SIMPLIFIED_CHINESE).format(discussions.createTime)
            poster.text = "$date by ${discussions.username}"
            votes.text = "+${RandomUtils.nextInt(0, 99)}"
            replys.text = "+${RandomUtils.nextInt(0, 99)}"
            relevantTagsLinearLayout.removeAllViews()
            val list = discussions.relevantTags.split(Regex(";"))
            list.forEach {
                val button = Button(mContext)
                button.layoutParams = tag.layoutParams
                button.text = it
                button.isClickable = false
                relevantTagsLinearLayout.addView(button)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView = itemView.findViewById(R.id.question)
        val tag: TextView = itemView.findViewById(R.id.tag)
        val relevantTagsLinearLayout: LinearLayout = itemView.findViewById(R.id.relevant_tags_linear_layout)
        val poster: TextView = itemView.findViewById(R.id.poster)
        val votes: TextView = itemView.findViewById(R.id.votes)
        val replys: TextView = itemView.findViewById(R.id.replys)
    }
}