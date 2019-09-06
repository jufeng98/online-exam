package org.javamaster.fragmentlearning.testActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.Fruit
import org.javamaster.fragmentlearning.testAdapter.FruitAdapterMenu
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity
import java.util.*

class MenuActivity : BaseAppActivity() {
    private val fruitList = mutableListOf(
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
    lateinit var adapter: FruitAdapterMenu
    override fun initContentView(): Int? {
        return R.layout.activity_menu
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
            Snackbar.make(view, "删除数据", Snackbar.LENGTH_SHORT)
                .setAction(
                    "撤销"
                ) {
                    Toast.makeText(this@MenuActivity, "恢复数据", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = layoutManager
        adapter = FruitAdapterMenu(fruitList)
        recycler_view.adapter = adapter
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh.setOnRefreshListener { refreshFruits() }
        textView.text = resources.getStringArray(R.array.fruits).joinToString(",")
        textView3.text = Html.fromHtml(getString(R.string.htmlWelcome))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        app_tool_bar.inflateMenu(R.menu.menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawer_layout.openDrawer(GravityCompat.START)
            else -> Toast.makeText(this, "You clicked " + item.title, Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun initFruits() {
        for (i in 0..49) {
            val random = Random()
            val index = random.nextInt(fruitList.size)
            fruitList.add(fruitList[index])
        }
    }

    private fun refreshFruits() {
        Thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                swipe_refresh.isRefreshing = false
            }
        }.start()
    }

    companion object {
        fun actonStart(context: Context) {
            var intent = Intent(context, MenuActivity::class.java)
            context.startActivity(intent)
        }
    }
}
