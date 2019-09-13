package org.javamaster.fragmentlearning.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.entity.Topics
import org.javamaster.fragmentlearning.ui.activities.SectionsActivity
import org.javamaster.fragmentlearning.view.RoundProgressBar


/**
 * @author yudong
 * @date 2019/9/7
 */
class LearnAdapter(private var topicsList: List<Topics>, private val needProgressBar: Boolean) :
    RecyclerView.Adapter<LearnAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.fragment_learn_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topicsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topics = topicsList[position]
        holder.myCourseProgressBar.progress = 0
        if (!needProgressBar) {
            holder.myCourseProgressBar.visibility = View.INVISIBLE
        }
        holder.topicsName.text = topics.topicsName
        val bitmap = BitmapFactory.decodeByteArray(topics.topicsCoverImage, 0, topics.topicsCoverImage.size)
        holder.topicsCoverImg.setImageBitmap(bitmap)
        holder.topicsCoverImg.setOnClickListener {
            SectionsActivity.actionStart(mContext, topics.topicsCode, topics.topicsName)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myCourseProgressBar: RoundProgressBar = itemView.findViewById(R.id.holder_progress_bar)
        val topicsCoverImg: CircleImageView = itemView.findViewById(R.id.holder_cover_img)
        val topicsName: TextView = itemView.findViewById(R.id.holder_name)
    }
}