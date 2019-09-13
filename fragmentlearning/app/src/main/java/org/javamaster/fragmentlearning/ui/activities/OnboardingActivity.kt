package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_onboarding.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.OnBoardingItem

/**
 * @author yudong
 * @date 2019/8/24
 */
class OnboardingActivity : BaseAppActivity() {
    private lateinit var viewList: MutableList<OnBoardingItem>
    private lateinit var switchRunnable: Runnable

    override fun initContentView(): Int? {
        return R.layout.activity_onboarding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewList()
        val adapter = object : PagerAdapter() {

            override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
                return arg0 === arg1
            }

            override fun getCount(): Int {
                return viewList.size + 2
            }

            fun getItemCount(): Int {
                return viewList.size
            }

            override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
                container.removeView(obj as View)
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val rootView = layoutInflater.inflate(
                    R.layout.view_onboarding_item,
                    container,
                    false
                ) as ViewGroup
                val item = viewList[(viewList.size + position - 1) % viewList.size]
                rootView.findViewById<ImageView>(R.id.onboarding_icon).setImageResource(item.iconRes)
                rootView.findViewById<TextView>(R.id.onboarding_title).setText(item.titleRes)
                rootView.findViewById<TextView>(R.id.onboarding_message).setText(item.messageRes)
                container.addView(rootView)
                return rootView
            }
        }
        view_pager.adapter = adapter
        switchRunnable = Runnable { view_pager.currentItem = (view_pager.currentItem + 1) % adapter.count }
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == adapter.count - 1 || position == 0) {
                    view_pager.postDelayed({
                        view_pager.setCurrentItem(if (position == 0) adapter.getItemCount() else 1, false)
                    }, 300)
                    chooseDot(if (position == 0) adapter.getItemCount() - 1 else 0)
                    return
                }
                chooseDot(position - 1)
                view_pager.removeCallbacks(switchRunnable)
                view_pager.postDelayed(switchRunnable, 5000)
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    private fun chooseDot(position: Int) {
        onboarding_button_group.findViewWithTag<RadioButton>("onboarding_button_$position").isChecked = true
    }

    @OnClick(R.id.start_sign_up_activity)
    fun startSignUpActivity() {
        SignUpActivity.actionStart(this)
    }

    @OnClick(R.id.start_login_activity)
    fun startLoginActivity() {
        LoginActivity.actionStart(this)
    }

    private fun initViewList() {
        viewList = mutableListOf()
        viewList.add(OnBoardingItem(R.drawable.tab_learn, R.string.onboarding_learn, R.string.onboarding_learn_msg))
        viewList.add(OnBoardingItem(R.drawable.tab_play, R.string.onboarding_play, R.string.onboarding_play_msg))
        viewList.add(OnBoardingItem(R.drawable.tab_practice, R.string.onboarding_code, R.string.onboarding_code_msg))
        viewList.add(
            OnBoardingItem(
                R.drawable.tab_discuss,
                R.string.onboarding_discuss,
                R.string.onboarding_discuss_msg
            )
        )
        viewList.add(
            OnBoardingItem(
                R.drawable.tab_profile,
                R.string.onboarding_profile,
                R.string.onboarding_profile_msg
            )
        )
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, OnboardingActivity::class.java)
            context.startActivity(intent)
        }
    }
}
