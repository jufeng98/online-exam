package org.javamaster.fragmentlearning.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.entity.Knowledges
import org.javamaster.fragmentlearning.data.entity.KnowledgesQuestionNumVo
import org.javamaster.fragmentlearning.ui.activities.KnowledgePointsActivity


/**
 * @author yudong
 * @date 2019/9/12
 */
class KnowledgesAdapter(
    private val knowledgesList: List<Knowledges>,
    private val questionNumVos: List<KnowledgesQuestionNumVo>
) :
    RecyclerView.Adapter<KnowledgesAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    private val map: MutableMap<String, Int> = mutableMapOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.learn_knowledge_item, parent, false)
        questionNumVos.forEach {
            map[it.knowledgesCode] = it.questionsNum
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return knowledgesList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val knowledges = knowledgesList[position]
        holder.numText.text = "${position + 1}/$itemCount"
        holder.knowledgesTitle.text = knowledges.knowledgesName
        val num = map[knowledges.knowledgesCode] ?: 0
        holder.questionsNum.text = "" + num + mContext.getString(R.string.question_num)
        holder.linearLayout.setOnClickListener {
            KnowledgePointsActivity.actionStart(mContext, knowledges.knowledgesCode)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numText: TextView = itemView.findViewById(R.id.num_text)
        val knowledgesTitle: TextView = itemView.findViewById(R.id.knowledges_title)
        val questionsNum: TextView = itemView.findViewById(R.id.questions_num)
        var linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout)
    }
}