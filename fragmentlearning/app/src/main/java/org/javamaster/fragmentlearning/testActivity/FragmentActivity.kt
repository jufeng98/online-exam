package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.OnClick
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.testFragment.AnotherCenterFragment
import org.javamaster.fragmentlearning.testFragment.CenterFragment

class FragmentActivity : AppCompatActivity() {
    private var switchBool = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        ButterKnife.bind(this)
        // 获取碎片
        supportFragmentManager.findFragmentById(R.id.frame_fragment_center)
    }

    @OnClick(R.id.left_switch_fragment)
    fun switchFragment(view: View) {
        switchBool = !switchBool
        if (switchBool) {
            replaceFragment(CenterFragment())
        } else {
            replaceFragment(AnotherCenterFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        var tran = supportFragmentManager.beginTransaction()
        tran.replace(R.id.frame_fragment_center, fragment)
        // 添加到调用栈中
        tran.addToBackStack(null)
        tran.commit()

    }
}
