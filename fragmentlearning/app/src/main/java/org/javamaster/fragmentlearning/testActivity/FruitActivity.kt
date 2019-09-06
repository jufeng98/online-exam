package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_fruit.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class FruitActivity : BaseAppActivity() {
    override fun initContentView(): Int? {
        return R.layout.activity_fruit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val fruitName = intent.getStringExtra(FRUIT_NAME)
        val fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.title = fruitName
        Glide.with(this).load(fruitImageId).into(fruit_image_view)
        val fruitContent = generateFruitContent(fruitName)
        fruit_content_text.text = fruitContent
    }

    private fun generateFruitContent(fruitName: String): String {
        val fruitContent = StringBuilder()
        for (i in 0..499) {
            fruitContent.append(fruitName)
        }
        return fruitContent.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMAGE_ID = "fruit_image_id"
    }
}
