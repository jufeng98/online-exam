package org.javamaster.fragmentlearning.testActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import org.javamaster.fragmentlearning.R

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        textView.text = resources.getStringArray(R.array.fruits).joinToString(",")
        textView3.text = Html.fromHtml(getString(R.string.htmlWelcome))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    fun onGroupItemClick(item: MenuItem) {
        // One of the group items (using the onClick attribute) was clicked
        // The item parameter passed here indicates which item it is
        // All other menu item clicks are handled by <code><a href="/reference/android/app/Activity.html#onOptionsItemSelected(android.view.MenuItem)">onOptionsItemSelected()</a></code>
    }

    companion object {
        fun actonStart(context: Activity) {
            var intent = Intent(context, MenuActivity::class.java)
            context.startActivity(intent)
        }
    }
}
