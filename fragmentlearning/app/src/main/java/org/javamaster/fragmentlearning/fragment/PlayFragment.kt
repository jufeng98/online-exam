package org.javamaster.fragmentlearning.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_play.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.ExamsAdapter
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.Exams
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import javax.inject.Inject

class PlayFragment : Fragment() {
    @Inject
    lateinit var learnService: LearnService
    private lateinit var examsList: MutableList<Exams>
    private lateinit var examsDisposable: Disposable
    private var refreshExamsDisposable: Disposable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onResume() {
        super.onResume()
        examsDisposable = Observable.create<MutableList<Exams>> {
            val examsList = learnService.findExamsList(true)
            it.onNext(examsList)
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            initAdapter(it)
        }, {
            OperationListener.fail(it)
        })
        swipe_refresh.setOnRefreshListener {
            refreshExamsDisposable = Observable.create<MutableList<Exams>> {
                val examsList = learnService.findExamsList(false)
                it.onNext(examsList)
                it.onComplete()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                swipe_refresh.isRefreshing = false
                examsList.clear()
                examsList.addAll(it)
                play_recycler_view.adapter!!.notifyDataSetChanged()
            }, {
                swipe_refresh.isRefreshing = false
                OperationListener.fail(it)
            })
        }
    }

    override fun onPause() {
        super.onPause()
        examsDisposable.dispose()
        refreshExamsDisposable?.dispose()
    }

    private fun initAdapter(examsList: MutableList<Exams>) {
        this.examsList = examsList
        val adapter = ExamsAdapter(this.examsList)
        play_recycler_view.layoutManager = LinearLayoutManager(activity)
        play_recycler_view.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayFragment()
    }
}
