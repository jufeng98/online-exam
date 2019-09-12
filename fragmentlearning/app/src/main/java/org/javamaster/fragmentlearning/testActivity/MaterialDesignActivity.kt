package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_material_design.*
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.Fruit
import org.javamaster.fragmentlearning.testAdapter.MaterialDesignFruitAdapter
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.util.*

class MaterialDesignActivity : BaseAppActivity() {

    private val fruitListCopy = mutableListOf(
        Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango)
    )
    private val fruitList: MutableList<Fruit> = mutableListOf()
    lateinit var adapterMaterialDesign: MaterialDesignFruitAdapter

    override fun initContentView(): Int? {
        return R.layout.activity_material_design
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(app_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_mini)
        nav_view.setCheckedItem(R.id.nav_call)
        nav_view.setNavigationItemSelectedListener {
            drawer_layout.closeDrawers()
            true
        }
        fab.setOnClickListener { view ->
            Snackbar.make(view, "即将删除数据...", Snackbar.LENGTH_SHORT)
                .setAction(
                    "撤销"
                ) {
                    Toast.makeText(this, "恢复数据", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        recycler_view_vertical.layoutManager = layoutManager
        adapterMaterialDesign = MaterialDesignFruitAdapter(fruitList)
        recycler_view_vertical.adapter = adapterMaterialDesign
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh.setOnRefreshListener { refreshFruits() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        app_tool_bar.inflateMenu(R.menu.menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> drawer_layout.openDrawer(GravityCompat.START)
        }
        return true
    }

    private fun initFruits() {
        fruitList.clear()
        for (i in 0..49) {
            val random = Random()
            val index = random.nextInt(fruitListCopy.size)
            fruitList.add(fruitListCopy[index])
        }
    }

    private fun refreshFruits() {
        Thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapterMaterialDesign.notifyDataSetChanged()
                swipe_refresh.isRefreshing = false
            }
        }.start()
    }

}
