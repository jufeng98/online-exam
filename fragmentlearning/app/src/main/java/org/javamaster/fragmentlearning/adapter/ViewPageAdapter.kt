package org.javamaster.fragmentlearning.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import org.javamaster.fragmentlearning.R

/**
 * @author yudong
 * @date 2019/8/23
 */
class ViewPageAdapter : PagerAdapter() {
    private val items = mutableListOf<Item>()

    override fun getCount(): Int {
        return items.size + 2
    }

    fun getItemCount(): Int {
        return items.size
    }

    private class Item(
        var iconRes: Int,
        var titleRes: Int,
        var messageRes: Int
    )

    init {
        this.items.add(Item(R.drawable.tab_learn, R.string.onboarding_learn, R.string.onboarding_learn_msg))
        this.items.add(Item(R.drawable.tab_play, R.string.onboarding_play, R.string.onboarding_play_msg))
        this.items.add(Item(R.drawable.tab_practice, R.string.onboarding_code, R.string.onboarding_code_msg))
        this.items.add(
            Item(
                R.drawable.tab_discuss,
                R.string.onboarding_discuss,
                R.string.onboarding_discuss_msg
            )
        )
        this.items.add(
            Item(
                R.drawable.tab_profile,
                R.string.onboarding_profile,
                R.string.onboarding_profile_msg
            )
        )
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rootView =
            LayoutInflater.from(container.context).inflate(R.layout.view_onboarding_item, container, false) as ViewGroup
        val item = this.items[(this.items.size + position - 1) % this.items.size]
        (rootView.findViewById<View>(R.id.onboarding_icon) as ImageView).setImageResource(item.iconRes)
        (rootView.findViewById<View>(R.id.onboarding_title) as TextView).setText(item.titleRes)
        (rootView.findViewById<View>(R.id.onboarding_message) as TextView).setText(item.messageRes)
        container.addView(rootView)
        return rootView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}