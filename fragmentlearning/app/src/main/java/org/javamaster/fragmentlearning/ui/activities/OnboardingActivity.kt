package org.javamaster.fragmentlearning.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_onboarding.*
import org.javamaster.fragmentlearning.R


class OnboardingActivity : BaseAppActivity() {
    private lateinit var viewList: MutableList<View>

    override fun initContentView(): Int? {
        return R.layout.activity_onboarding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewList()
        var viewPager: ViewPager = viewpager as ViewPager
        val pagerAdapter = object : PagerAdapter() {

            override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
                return arg0 === arg1
            }

            override fun getCount(): Int {
                return viewList.size
            }

            override fun destroyItem(
                container: ViewGroup, position: Int,
                `object`: Any
            ) {
                container.removeView(viewList[position])
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                container.addView(viewList[position])
                return viewList[position]
            }
        }
        viewPager.adapter = pagerAdapter
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            @SuppressLint("LongLogTag")
            override fun onPageScrollStateChanged(state: Int) {
                Log.i(this::class.qualifiedName + " onPageScrollStateChanged", state.toString())
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.i(
                    this::class.qualifiedName + " onPageScrolled",
                    "$position $positionOffset $positionOffsetPixels"
                )
            }

            override fun onPageSelected(position: Int) {
                Log.i(this::class.qualifiedName + " onPageScrolled", position.toString())
            }

        })
    }

    @OnClick(R.id.start_login_activity)
    fun startLoginActivity() {
        LoginActivity.actionStart(this)
    }

    @OnClick(R.id.start_sign_up_activity)
    fun startSignUpActivity() {
        SignUpActivity.actionStart(this)
    }

    private fun initViewList() {
        viewList = mutableListOf()
        viewList.add(layoutInflater.inflate(R.layout.onboarding_learn, null))
        viewList.add(layoutInflater.inflate(R.layout.onboarding_play, null))
        viewList.add(layoutInflater.inflate(R.layout.onboarding_code, null))
        viewList.add(layoutInflater.inflate(R.layout.onboarding_discuss, null))
        viewList.add(layoutInflater.inflate(R.layout.onboarding_your_profile, null))
    }

    companion object {
        fun actionStart(context: Activity) {
            var intent = Intent(context, OnboardingActivity::class.java)
            context.startActivity(intent)
        }
    }
}
