package org.javamaster.fragmentlearning.testAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.javamaster.fragmentlearning.R

/**
 * @author yudong
 * @date 2019/9/9
 */
class ExerciseArrayAdapter(
    context: Context,
    private val resourcesId: Int,
    val list: List<Pair<String, Class<out Any>>>
) : ArrayAdapter<String>(context, resourcesId) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val pair = list[position]
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourcesId, null, false)
            val textView = view.findViewById<TextView>(R.id.text_view)
            textView.text = pair.first
            viewHolder = ViewHolder(textView, pair.second)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
            viewHolder.textView.text = pair.first
        }
        return view
    }

    override fun getCount(): Int {
        return list.size
    }

    data class ViewHolder(val textView: TextView, val clz: Class<out Any>)
}