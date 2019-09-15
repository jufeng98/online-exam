package org.javamaster.fragmentlearning.testAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fruit_item_layout.view.*
import org.javamaster.fragmentlearning.data.model.Fruit

/**
 * @author yudong
 * @date 2019/8/24
 */
class FruitAdapter(context: Context, private var textViewResourcesId: Int, fruits: List<Fruit>) :
    ArrayAdapter<Fruit>(context, textViewResourcesId, fruits) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val fruit = getItem(position)
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = convertView ?: LayoutInflater.from(context).inflate(
                textViewResourcesId,
                parent,
                false
            )
            viewHolder = ViewHolder(view.fruit_image, view.fruit_name)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.imageView.setImageResource(fruit.imageId)
        viewHolder.textView.text = fruit.name
        return view
    }
}

data class ViewHolder(val imageView: ImageView, val textView: TextView)