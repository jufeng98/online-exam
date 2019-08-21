package org.javamaster.fragmentlearning.test

import android.os.Bundle
import androidx.fragment.app.Fragment


/**
 * 将有状态的对象保留在片段,切勿传递与 Activity 绑定的对象，例如，Drawable、Adapter、View 或其他任何与 Context 关联的对象。
 * 否则，它将泄漏原始 Activity 实例的所有视图和资源。
 * @author yudong
 * @date 2019/8/21
 */
class RetainedFragment : Fragment() {
    var data: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retain this fragment
        retainInstance = true
    }

    override fun toString(): String {
        return "RetainedFragment(data=$data)"
    }

}