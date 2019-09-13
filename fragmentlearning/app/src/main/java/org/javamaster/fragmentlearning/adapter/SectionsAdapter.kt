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
import org.javamaster.fragmentlearning.data.entity.Sections
import org.javamaster.fragmentlearning.ui.activities.KnowledgesActivity
import org.javamaster.fragmentlearning.view.RoundProgressBar


/**
 * @author yudong
 * @date 2019/9/12
 */
class SectionsAdapter(private var sectionsList: List<Sections>) : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {
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
        holder.myCourseProgressBar.progress = 0
        holder.topicsName.text = sections.sectionsName
        val bitmap = BitmapFactory.decodeByteArray(sections.sectionsCoverImage, 0, sections.sectionsCoverImage.size)
        holder.topicsCoverImg.setImageBitmap(bitmap)
        holder.topicsCoverImg.setOnClickListener {
            KnowledgesActivity.actionStart(mContext, sections.sectionsCode, sections.sectionsName)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myCourseProgressBar: RoundProgressBar = itemView.findViewById(R.id.holder_progress_bar)
        var topicsCoverImg: CircleImageView = itemView.findViewById(R.id.holder_cover_img)
        var topicsName: TextView = itemView.findViewById(R.id.holder_name)
    }
}