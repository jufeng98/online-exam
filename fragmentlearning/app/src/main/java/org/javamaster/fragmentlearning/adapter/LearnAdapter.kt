package org.javamaster.fragmentlearning.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.model.Topics
import org.javamaster.fragmentlearning.view.RoundProgressBar


/**
 * @author yudong
 * @date 2019/9/7
 */
class LearnAdapter(var topicsList: List<Topics>, private val needProgressBar: Boolean) :
    RecyclerView.Adapter<LearnAdapter.ViewHolder>() {
    private var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.fragment_learn_item, parent, false)
        val holder = ViewHolder(view)
        holder.topicsCoverImg.setOnClickListener {

        }
        return holder
    }

    override fun getItemCount(): Int {
        return topicsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var topics = topicsList[position]
        holder.myCourseProgressBar.progress = 0
        if (!needProgressBar) {
            holder.myCourseProgressBar.visibility = View.INVISIBLE
        }
        holder.topicsName.text = topics.topicsName
        val bitmap = BitmapFactory.decodeByteArray(topics.topicsCoverImage, 0, topics.topicsCoverImage.size)
        holder.topicsCoverImg.setImageBitmap(bitmap)
        holder.topicsCoverImg.setOnClickListener {
            Toast.makeText(App.context, R.string.app_completing, Toast.LENGTH_SHORT).show()
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myCourseProgressBar: RoundProgressBar = itemView.findViewById(R.id.my_course_progress_bar)
        var topicsCoverImg: CircleImageView = itemView.findViewById(R.id.topics_cover_img)
        var topicsName: TextView = itemView.findViewById(R.id.topics_name)
    }
}