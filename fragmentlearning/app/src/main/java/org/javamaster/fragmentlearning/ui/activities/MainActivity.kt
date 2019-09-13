package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import butterknife.OnClick
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_bottom_layout.*
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.model.User
import org.javamaster.fragmentlearning.fragment.*
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.service.LoginService
import org.javamaster.fragmentlearning.utils.ImageUtils
import javax.inject.Inject


/**
 * @author yudong
 * @date 2019/8/18
 */
class MainActivity : BaseAppActivity() {

    @Inject
    lateinit var loginService: LoginService

    lateinit var loginUserInfo: User
    private var fragmentMap = mutableMapOf<Int, Fragment>()

    override fun initContentView(): Int? {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        val preferences = App.getLoginSharedPreferences()
        val jsonStr = preferences.getString(LoginService.LOGIN_USER_INFO, "")
        loginUserInfo = App.objectMapper.readValue(jsonStr, User::class.java)
        main_nav_view.getHeaderView(0).findViewById<TextView>(R.id.username).text = loginUserInfo.username
        main_nav_view.getHeaderView(0).findViewById<TextView>(R.id.user_email).text = loginUserInfo.email
        val bitmap = ImageUtils.getUserPhoto()
        if (bitmap != null) {
            main_nav_view.getHeaderView(0).findViewById<CircleImageView>(R.id.user_photo).setImageBitmap(bitmap)
        }
        app_tool_bar.title = getString(R.string.onboarding_learn)
        setSupportActionBar(app_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_mini)
        main_nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_exit -> {
                    loginService.logout()
                    OnboardingActivity.actionStart(this@MainActivity)
                    App.finishExcept(mutableSetOf(OnboardingActivity::class.java))
                }
                else -> Toast.makeText(this@MainActivity, R.string.app_completing, Toast.LENGTH_SHORT).show()
            }
            true
        }

        val fragment = LearnFragment.newInstance()
        fragmentMap[R.id.tab0] = fragment
        replaceFragment(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        app_tool_bar.inflateMenu(R.menu.menu_main)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                main_drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @OnClick(R.id.tab0, R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4)
    fun tabClick(tabButton: Button) {
        resetTabImg()
        var fragment: Fragment? = fragmentMap[tabButton.id]
        when (tabButton.id) {
            R.id.tab0 -> {
                tab0.changeTopImgAndColor(R.drawable.tab_learn_mini_choose, R.color.tabTextColorChoose)
                app_tool_bar.title = getString(R.string.onboarding_learn)
                replaceFragment(fragment!!)
            }
            R.id.tab1 -> {
                tab1.changeTopImgAndColor(R.drawable.tab_play_mini_choose, R.color.tabTextColorChoose)
                app_tool_bar.title = getString(R.string.onboarding_play)
                if (fragment == null) {
                    fragment = PlayFragment.newInstance("", "")
                }
                replaceFragment(fragment)
            }
            R.id.tab2 -> {
                tab2.changeTopImgAndColor(R.drawable.tab_home_mini_choose, R.color.tabTextColorChoose)
                app_tool_bar.title = getString(R.string.onboarding_home)
                if (fragment == null) {
                    fragment = HomeFragment.newInstance("", "")
                }
                replaceFragment(fragment)
            }
            R.id.tab3 -> {
                tab3.changeTopImgAndColor(R.drawable.tab_practice_mini_choose, R.color.tabTextColorChoose)
                app_tool_bar.title = getString(R.string.onboarding_code)
                if (fragment == null) {
                    fragment = PracticeFragment.newInstance("", "")
                }
                replaceFragment(fragment)
            }
            R.id.tab4 -> {
                tab4.changeTopImgAndColor(R.drawable.tab_discuss_mini_choose, R.color.tabTextColorChoose)
                app_tool_bar.title = getString(R.string.onboarding_discuss)
                if (fragment == null) {
                    fragment = DiscussFragment.newInstance("", "")
                }
                replaceFragment(fragment)
            }
        }
        fragmentMap[tabButton.id] = fragment!!
    }

    private fun resetTabImg() {
        tab0.changeTopImgAndColor(R.drawable.tab_learn_mini, R.color.colorAccent)
        tab1.changeTopImgAndColor(R.drawable.tab_play_mini, R.color.colorAccent)
        tab2.changeTopImgAndColor(R.drawable.tab_home_mini, R.color.colorAccent)
        tab3.changeTopImgAndColor(R.drawable.tab_practice_mini, R.color.colorAccent)
        tab4.changeTopImgAndColor(R.drawable.tab_discuss_mini, R.color.colorAccent)
    }

    @Suppress("DEPRECATION")
    private fun Button.changeTopImgAndColor(img: Int, color: Int) {
        val drawable = resources.getDrawable(img, theme)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        this.setCompoundDrawables(null, drawable, null, null)
        this.setTextColor(resources.getColor(color))
    }

    private fun replaceFragment(fragment: Fragment) {
        val tran = supportFragmentManager.beginTransaction()
        tran.replace(R.id.main_frame_fragment, fragment)
        tran.commit()
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
