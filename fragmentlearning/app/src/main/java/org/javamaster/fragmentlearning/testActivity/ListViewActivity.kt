package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list_view.*
import org.javamaster.fragmentlearning.R

class ListViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        var fruits = resources.getStringArray(R.array.fruits)
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fruits)
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            var choose = fruits[position]
            Toast.makeText(this@ListViewActivity, "you click the $choose item!", Toast.LENGTH_SHORT).show()
        }
    }
}
