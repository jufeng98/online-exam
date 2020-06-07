package org.javamaster.fragmentlearning.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.ActionProvider
import org.javamaster.fragmentlearning.R

/**
 * @author yudong
 * @date 2019/9/20
 */
class BadgeActionProvider(context: Context) : ActionProvider(context) {
    private lateinit var mIvIcon: ImageView
    private lateinit var mTvBadge: TextView
    private var onClickListener: View.OnClickListener? = null


    @SuppressLint("InflateParams", "PrivateResource")
    override fun onCreateActionView(): View {
        val size = context.resources.getDimensionPixelSize(
            R.dimen.abc_action_bar_default_height_material
        )
        val layoutParams = ViewGroup.LayoutParams(size, size)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.menu_view_badge, null, false)
        view.layoutParams = layoutParams
        mIvIcon = view.findViewById(R.id.iv_icon)
        mTvBadge = view.findViewById(R.id.tv_badge)
        view.setOnClickListener(onClickListener)
        return view
    }

    fun setOnClickListener(onClickListener: View.OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun hideRedDot() {
        mTvBadge.visibility = View.INVISIBLE
    }

    fun showRedDot() {
        mTvBadge.visibility = View.VISIBLE
    }

    fun setIcon(@DrawableRes icon: Int) {
        mIvIcon.setImageResource(icon)
    }

    @SuppressLint("SetTextI18n")
    fun setBadge(i: Int) {
        mTvBadge.text = i.toString()
    }

    fun setTextInt(@StringRes i: Int) {
        mTvBadge.setText(i)
    }

    fun setText(i: CharSequence) {
        mTvBadge.text = i
    }
}
