package org.javamaster.fragmentlearning.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App.Companion.context

/**
 * @author yudong
 * @date 2019/8/24
 */
class CountryAdapter(private var textViewResourcesId: Int, val list: List<Pair<Int, Int>>) :
    BaseAdapter() {
    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val pair = list[position]
        val view: View
        if (convertView == null) {
            view = convertView ?: LayoutInflater.from(context).inflate(
                textViewResourcesId,
                parent,
                false
            )
        } else {
            view = convertView
        }
        view.findViewById<ImageView>(R.id.country_image).setImageResource(pair.first)
        view.findViewById<TextView>(R.id.country_text).text = context.getString(pair.second)
        return view
    }

}