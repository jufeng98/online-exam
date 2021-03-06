package org.javamaster.fragmentlearning.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.entity.Sections
import org.javamaster.fragmentlearning.ui.activities.KnowledgesActivity
import org.javamaster.fragmentlearning.view.RoundProgressBar


/**
 * @author yudong
 * @date 2019/9/12
 */
class SectionsAdapter(
    private var sectionsList: List<Sections>,
    private var progressMap: Map<String, Int>
) : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.learn_section_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sectionsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sections = sectionsList[position]
        val progress = progressMap[sections.sectionsCode] ?: 0
        holder.myCourseProgressBar.progress = progress
        holder.topicsName.text = sections.sectionsName
        Glide.with(mContext).load(sections.sectionsCoverImage).into(holder.topicsCoverImg)
        holder.topicsCoverImg.setOnClickListener {
            KnowledgesActivity.actionStart(mContext, sections.sectionsCode, sections.sectionsName)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myCourseProgressBar: RoundProgressBar = itemView.findViewById(R.id.holder_progress_bar)
        val topicsCoverImg: CircleImageView = itemView.findViewById(R.id.holder_cover_img)
        val topicsName: TextView = itemView.findViewById(R.id.holder_name)
    }
}