package org.javamaster.fragmentlearning.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_play.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.ExamsAdapter
import org.javamaster.fragmentlearning.asyncTask.ExamsAsyncTask
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.Exams
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import org.litepal.LitePal
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

    override fun onResume() {
        super.onResume()
        val listener = object : OperationListener<List<Exams>> {
            override fun success(t: List<Exams>) {
                swipe_refresh.isRefreshing = false
                initAdapter(t)
            }

            override fun fail(errorCode: Int, errorMsg: String) {
                swipe_refresh.isRefreshing = false
                super.fail(errorCode, errorMsg)
            }
        }
        val examsList = LitePal.findAll(Exams::class.java)
        if (examsList.isNotEmpty()) {
            initAdapter(examsList)
        } else {
            swipe_refresh.isRefreshing = true
            ExamsAsyncTask(learnService, listener).execute()
        }
        swipe_refresh.setOnRefreshListener {
            ExamsAsyncTask(learnService, listener).execute()
        }
    }

    private fun initAdapter(examsList: List<Exams>) {
        val adapter = ExamsAdapter(examsList)
        play_recycler_view.layoutManager = LinearLayoutManager(activity)
        play_recycler_view.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayFragment()
    }
}
