package org.javamaster.fragmentlearning.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_examming.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.ExamsQuestionsAdapter
import org.javamaster.fragmentlearning.asyncTask.QuestionsAsyncTask
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.ExamQuestionsVo
import org.javamaster.fragmentlearning.data.entity.OptionsVo
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import org.litepal.LitePal
import javax.inject.Inject

class ExamingActivity : BaseAppActivity() {
    @Inject
    lateinit var learnService: LearnService

    private lateinit var examsCode: String
    private val listener = object : OperationListener<List<ExamQuestionsVo>> {
        override fun success(t: List<ExamQuestionsVo>) {
            LitePal.deleteAll(ExamQuestionsVo::class.java, "examsCode=?", examsCode)
            t.forEach {
                LitePal.deleteAll(OptionsVo::class.java, "questionsCode=?", it.questionsCode)
                it.examsCode = examsCode
                it.optionsVos.forEach { optionsVo: OptionsVo ->
                    optionsVo.questionsCode = it.questionsCode
                }
                LitePal.saveAll(it.optionsVos)
            }
            LitePal.saveAll(t)
            initAdapter(t)
            loading.visibility = View.GONE
        }

        override fun fail(errorCode: Int, errorMsg: String) {
            loading.visibility = View.GONE
            super.fail(errorCode, errorMsg)
        }
    }

    override fun initContentView(): Int? {
        return R.layout.activity_examming
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        examsCode = intent.getStringExtra("examsCode")
        val list = LitePal.where("examsCode=?", examsCode).find(ExamQuestionsVo::class.java)
        if (list.isNotEmpty()) {
            list.forEach {
                it.optionsVos = LitePal.where("questionsCode=?", it.questionsCode).find(OptionsVo::class.java)
            }
            initAdapter(list)
            return
        }
        loading.visibility = View.VISIBLE
        QuestionsAsyncTask(learnService, listener).execute(examsCode)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.addSubMenu(1, 1, Menu.NONE, "刷新试题")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                loading.visibility = View.VISIBLE
                QuestionsAsyncTask(learnService, listener).execute(examsCode)
            }
        }
        return true
    }

    @Suppress("DEPRECATION")
    fun initAdapter(list: List<ExamQuestionsVo>) {
        options_radio_group.removeAllViews()
        list.forEach { _ ->
            val radioButton = RadioButton(this)
            options_radio_group.addView(radioButton)
        }
        val radioButton = options_radio_group.getChildAt(0) as RadioButton
        radioButton.isChecked = true
        val adapter = ExamsQuestionsAdapter(list, this)
        questions_view_pager.adapter = adapter
        questions_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                chooseDot(position)
                if (position == list.size) {
                    constructSubmitPage(position)
                } else {
                    reversePage()
                }
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    @SuppressLint("RestrictedApi")
    private fun constructSubmitPage(position: Int) {
        val adapter = questions_view_pager.adapter as ExamsQuestionsAdapter
        adapter.constructSubmitPage(position)
        options_radio_group.visibility = View.INVISIBLE
        next.visibility = View.INVISIBLE
        submit.visibility = View.VISIBLE
    }

    @SuppressLint("RestrictedApi")
    private fun reversePage() {
        options_radio_group.visibility = View.VISIBLE
        next.visibility = View.VISIBLE
        submit.visibility = View.GONE
    }

    @OnClick(R.id.previous)
    fun previousQuestions() {
        val position = questions_view_pager.currentItem - 1
        if (position < 0) {
            Toast.makeText(this, getString(R.string.already_first), Toast.LENGTH_SHORT).show()
            return
        }
        questions_view_pager.currentItem = position
        chooseDot(position)
    }

    @OnClick(R.id.next)
    fun nextQuestions() {
        val position = questions_view_pager.currentItem + 1
        if (position >= questions_view_pager.adapter!!.count) {
            Toast.makeText(this, getString(R.string.already_last), Toast.LENGTH_SHORT).show()
            return
        }
        questions_view_pager.currentItem = position
        chooseDot(position)
    }

    @OnClick(R.id.submit)
    fun submitAnswers() {
        val adapter = questions_view_pager.adapter as ExamsQuestionsAdapter
        val list = adapter.getAnswers()
        Log.i(this::class.qualifiedName, App.objectMapper.writeValueAsString(list))
    }

    fun chooseDot(position: Int) {
        if (options_radio_group.getChildAt(position) == null) {
            return
        }
        val radioButton = options_radio_group.getChildAt(position) as RadioButton
        radioButton.isChecked = true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode != KeyEvent.KEYCODE_BACK || event.action != KeyEvent.ACTION_DOWN) {
            return super.onKeyDown(keyCode, event)
        }
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.dialog_title_warm_tip)
        alertDialog.setMessage(getString(R.string.lose_exams_progress))
        alertDialog.setPositiveButton(R.string.cancel) { _, _ ->
        }
        alertDialog.setNegativeButton(R.string.dialog_confirm) { _, _ ->
            finish()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
        return true
    }

    companion object {
        fun actionStart(context: Context, examsCode: String) {
            val intent = Intent(context, ExamingActivity::class.java)
            intent.putExtra("examsCode", examsCode)
            context.startActivity(intent)
        }
    }

}
