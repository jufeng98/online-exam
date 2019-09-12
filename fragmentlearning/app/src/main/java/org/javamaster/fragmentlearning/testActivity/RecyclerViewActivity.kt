package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.Fruit
import org.javamaster.fragmentlearning.testAdapter.RecyclerFruitAdapter
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class RecyclerViewActivity : BaseAppActivity() {

    var images = listOf(
        R.drawable.tab_discuss,
        R.drawable.tab_home,
        R.drawable.tab_learn,
        R.drawable.tab_play,
        R.drawable.tab_notifications
    )

    private lateinit var fruitList: List<Fruit>

    override fun initContentView(): Int? {
        return R.layout.activity_recycler_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFruits()

        var recyclerView: RecyclerView = recycler_view_vertical as RecyclerView
        var linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        var adapter = RecyclerFruitAdapter(1, fruitList)
        recyclerView.adapter = adapter

        var recyclerViewHorizontal: RecyclerView = recycler_view_horizontal as RecyclerView
        var linearLayoutManager1 = LinearLayoutManager(this)
        linearLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewHorizontal.layoutManager = linearLayoutManager1
        var adapter1 = RecyclerFruitAdapter(2, fruitList)
        recyclerViewHorizontal.adapter = adapter1

        var recyclerViewWaterfall: RecyclerView = recycler_view_waterfall as RecyclerView
        var linearLayoutManager2 = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewWaterfall.layoutManager = linearLayoutManager2
        var adapter2 = RecyclerFruitAdapter(3, initFruits1())
        recyclerViewWaterfall.adapter = adapter2
    }

    private fun initFruits() {
        var fruits = resources.getStringArray(R.array.fruits)
        var i = 1
        fruitList = fruits.map {
            Fruit("${i++}.$it", images[Math.round(Math.random() * 4).toInt()])
        }
    }

    private fun initFruits1(): List<Fruit> {
        var i = 1
        var fruits = resources.getStringArray(R.array.fruits)
        return fruits.map {
            var num: Int = Math.ceil(Math.random() * 10).toInt()
            var str = "${i++}."
            for (i in 0..num) {
                str += " $it"
            }
            Fruit(str, images[Math.round(Math.random() * 4).toInt()])
        }
    }
}
