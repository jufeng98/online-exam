package org.javamaster.fragmentlearning.fragment

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.listener.SoftKeyBoardListener

/**
 * @author yudong
 * @date 2019/10/9
 */
open class MainFragment : Fragment() {

    private lateinit var fragmentActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentActivity = activity!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        SoftKeyBoardListener(fragmentActivity, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                fragmentActivity.findViewById<View>(R.id.include_tab).visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                val tab = fragmentActivity.findViewById<View>(R.id.include_tab)
                tab.alpha = 0f
                tab.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(tab, "alpha", 0f, 1f)
                    .apply {
                        duration = 300
                        start()
                    }
            }
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}