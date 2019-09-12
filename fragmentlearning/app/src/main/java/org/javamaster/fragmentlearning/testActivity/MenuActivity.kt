package org.javamaster.fragmentlearning.testActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.testFragment.CenterFragment
import org.javamaster.fragmentlearning.testFragment.LeftFragment
import org.javamaster.fragmentlearning.testFragment.RightFragment
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class MenuActivity : BaseAppActivity(), ActionBar.TabListener {
    override fun onTabReselected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {
    }

    override fun onTabUnselected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {
    }

    override fun onTabSelected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {
        var fragment: Fragment
        when {
            tab?.position == 0 -> fragment = LeftFragment()
            tab?.position == 1 -> fragment = CenterFragment()
            else -> fragment = RightFragment()
        }
        var transaction = this@MenuActivity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun initContentView(): Int? {
        return R.layout.activity_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "hello world"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_mini)
        supportActionBar?.navigationMode = ActionBar.NAVIGATION_MODE_TABS

        var tab0 = supportActionBar?.newTab()
            ?.setText("this is tab0")
            ?.setTabListener(this)
        supportActionBar?.addTab(tab0, 0, true)

        var tab1 = supportActionBar?.newTab()
            ?.setText("this is tab1")
            ?.setTabListener(this)
        supportActionBar?.addTab(tab1, 1)

        var tab2 = supportActionBar?.newTab()
            ?.setText("this is tab2")
            ?.setTabListener(this)
        supportActionBar?.addTab(tab2, 2)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Toast.makeText(this, "You clicked " + item!!.title, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun actonStart(context: Context) {
            var intent = Intent(context, MenuActivity::class.java)
            context.startActivity(intent)
        }
    }
}
