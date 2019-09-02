package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.Fruit
import org.javamaster.fragmentlearning.testAdapter.RecyclerFruitAdapter
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class StaggeredViewActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_staggered_view
    }

    private lateinit var images: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        images = listOf(
            R.drawable.tab_discuss,
            R.drawable.tab_home,
            R.drawable.tab_learn,
            R.drawable.tab_play,
            R.drawable.tab_notifications
        )
        var fruits = resources.getStringArray(R.array.fruits)
        var fruitList = fruits.map {
            var num: Int = Math.ceil(Math.random() * 10).toInt()
            var str = ""
            for (i in 0..num) {
                str += " $it"
            }
            Fruit(str, images[Math.round(Math.random() * 4).toInt()])
        }
        var recyclerView: RecyclerView = recycler_view as RecyclerView
        var layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        var adapter = RecyclerFruitAdapter(fruitList)
        recyclerView.adapter = adapter
    }
}
