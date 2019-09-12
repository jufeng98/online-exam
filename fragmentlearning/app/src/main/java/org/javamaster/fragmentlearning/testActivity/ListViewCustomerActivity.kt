package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list_view.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.Fruit
import org.javamaster.fragmentlearning.testAdapter.FruitAdapter
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class ListViewCustomerActivity : BaseAppActivity() {

    override fun initContentView(): Int? {
        return R.layout.activity_list_view_customer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var images = listOf(
            R.drawable.pineapple,
            R.drawable.banana,
            R.drawable.orange,
            R.drawable.mango,
            R.drawable.cherry
        )
        var fruits = resources.getStringArray(R.array.fruits)
        var fruitList = fruits.map { Fruit(it, images[Math.round(Math.random() * 4).toInt()]) }
        var fruitAdapter = FruitAdapter(this, R.layout.fruit_item_layout, fruitList)
        listView.adapter = fruitAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            var fruit = fruitList[position]
            Toast.makeText(this@ListViewCustomerActivity, "you click the ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
    }
}
