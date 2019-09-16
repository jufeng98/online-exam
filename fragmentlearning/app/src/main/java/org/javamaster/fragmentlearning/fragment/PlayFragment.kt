package org.javamaster.fragmentlearning.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.service.LearnService
import javax.inject.Inject

class PlayFragment : Fragment() {
    @Inject
    lateinit var learnService: LearnService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() = PlayFragment()
    }
}
