package org.javamaster.fragmentlearning.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.entity.Exams
import org.javamaster.fragmentlearning.ui.activities.ExamingActivity


/**
 * @author yudong
 * @date 2019/9/16
 */
class ExamsAdapter(private var examsList: List<Exams>) : RecyclerView.Adapter<ExamsAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.learn_exams_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return examsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exams = examsList[position]
        holder.examsName.text = exams.examsName
        holder.examsDesc.text = exams.examsDesc
        holder.startExams.setOnClickListener {
            ExamingActivity.actionStart(mContext, exams.examsCode)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val examsName: TextView = itemView.findViewById(R.id.exams_name)
        val examsDesc: TextView = itemView.findViewById(R.id.exams_desc)
        val startExams: Button = itemView.findViewById(R.id.start_exams)
    }
}