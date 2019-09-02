package org.javamaster.fragmentlearning.testFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.javamaster.fragmentlearning.R

class RightFragment : Fragment() {

    override fun onCreateView(///
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 获取活动实例
        var fragmentActivity = activity
        fragmentActivity?.title
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_right, container, false)
    }


}
