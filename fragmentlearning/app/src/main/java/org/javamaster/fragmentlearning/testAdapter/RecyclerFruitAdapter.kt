package org.javamaster.fragmentlearning.testAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.Fruit

/**
 * @author yudong
 * @date 2019/8/25
 */
class RecyclerFruitAdapter(val type: Int, private val fruitList: List<Fruit>) :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        var view = when (type) {
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.fruit_item_layout, parent, false)
            2 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.fruit_item_horizontal_layout, parent, false) // 横向滚动布局
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.fruit_item_staggered_layout, parent, false) // 瀑布流布局
        }
        var holder = RecyclerViewHolder(view)
        holder.view.setOnClickListener {
            var position = holder.adapterPosition
            var fruit = fruitList[position]
            Toast.makeText(view.context, "you click the ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
        return holder
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }

}

data class RecyclerViewHolder(val view: View) :
    RecyclerView.ViewHolder(view) {
    val fruitImage: ImageView = view.findViewById(R.id.fruit_image)
    val fruitName: TextView = view.findViewById(R.id.fruit_name)

}