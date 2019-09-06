package org.javamaster.fragmentlearning.testAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.Fruit
import org.javamaster.fragmentlearning.testActivity.FruitActivity

class FruitAdapterMenu(private val mFruitList: List<Fruit>) : RecyclerView.Adapter<FruitAdapterMenu.ViewHolder>() {

    private var mContext: Context? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardView: CardView = view as CardView
        var fruitImage: ImageView = view.findViewById<View>(R.id.fruit_image) as ImageView
        var fruitName: TextView = view.findViewById<View>(R.id.fruit_name) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false)
        val holder = ViewHolder(view)
        holder.cardView.setOnClickListener {
            val position = holder.adapterPosition
            val (name, imageId) = mFruitList[position]
            val intent = Intent(mContext, FruitActivity::class.java)
            intent.putExtra(FruitActivity.FRUIT_NAME, name)
            intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, imageId)
            mContext!!.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, imageId) = mFruitList[position]
        holder.fruitName.text = name
        Glide.with(mContext).load(imageId).into(holder.fruitImage)
    }

    override fun getItemCount(): Int {
        return mFruitList.size
    }

}
