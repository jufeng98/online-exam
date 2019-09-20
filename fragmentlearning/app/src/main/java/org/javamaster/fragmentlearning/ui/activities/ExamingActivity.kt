package org.javamaster.fragmentlearning.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_examming.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.adapter.ExamsQuestionsAdapter
import org.javamaster.fragmentlearning.adapter.Type
import org.javamaster.fragmentlearning.asyncTask.QuestionsAsyncTask
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.ExamQuestionsVo
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import org.javamaster.fragmentlearning.ui.viewModel.ExamingViewPageModel
import javax.inject.Inject

/**
 * @author yudong
 * @date 2019/9/12
 */
class ExamingActivity : BaseAppActivity() {
    @Inject
    lateinit var learnService: LearnService
    @Inject
    lateinit var examingViewPageModel: ExamingViewPageModel
    lateinit var adapter: ExamsQuestionsAdapter
    private lateinit var examsCode: String
    private val listener = object : OperationListener<List<ExamQuestionsVo>> {
        override fun success(t: List<ExamQuestionsVo>) {
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

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        examsCode = intent.getStringExtra("examsCode")

        examingViewPageModel.activityExamingState.observe(this, Observer {
            var value = if (it.showReturnSubmitButton) View.VISIBLE else View.INVISIBLE
            return_submit.visibility = value
            value = if (it.showSubmitButton) View.VISIBLE else View.INVISIBLE
            submit.visibility = value
            value = if (it.showQuestionsDot) View.VISIBLE else View.INVISIBLE
            questions_radio_group.visibility = value
            value = if (it.showNextButton) View.VISIBLE else View.INVISIBLE
            next.visibility = value
            if (it.constructSubmitPage) {
                adapter.constructSubmitOrResultPage()
            }
            chooseDot(it.position)
        })

        examingViewPageModel.answerResultState.observe(this, Observer {
            loading.visibility = View.GONE
            if (it.showError != null) {
                if (it.showError!!) {
                    Toast.makeText(this@ExamingActivity, it.errorMsg, Toast.LENGTH_SHORT).show()
                }
                return@Observer
            }
            adapter.changeType(it.type)
            adapter.initSubmitAnswersResVo(it.submitAnswersResVo!!)
            val rootView = questions_view_pager.getChildAt(adapter.getItemCount() - 1)
            adapter.fillRootView(rootView, adapter.getItemCount() - 1, true)
            examingViewPageModel.changePage(adapter.getItemCount(), adapter)
        })

        val examQuestionsVos = LearnService.getExamQuestionsVos(examsCode)
        if (examQuestionsVos.isNotEmpty()) {
            initAdapter(examQuestionsVos)
            return
        }
        loading.visibility = View.VISIBLE
        QuestionsAsyncTask(learnService, listener).execute(examsCode)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.refresh_question))
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
        questions_radio_group.removeAllViews()
        list.forEach { _ ->
            val radioButton = RadioButton(this)
            radioButton.isClickable = false
            questions_radio_group.addView(radioButton)
        }
        val radioButton = questions_radio_group.getChildAt(0) as RadioButton
        radioButton.isChecked = true
        adapter = ExamsQuestionsAdapter(list, this)
        questions_view_pager.adapter = adapter
        questions_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                examingViewPageModel.changePage(position, adapter)
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    @OnClick(R.id.previous)
    fun previousQuestions() {
        val position = questions_view_pager.currentItem - 1
        if (position < 0) {
            Toast.makeText(this, getString(R.string.already_first), Toast.LENGTH_SHORT).show()
            return
        }
        questions_view_pager.currentItem = position
        examingViewPageModel.changePage(position, adapter)
    }

    @OnClick(R.id.next)
    fun nextQuestions() {
        val position = questions_view_pager.currentItem + 1
        if (position >= questions_view_pager.adapter!!.count) {
            return
        }
        questions_view_pager.currentItem = position
        examingViewPageModel.changePage(position, adapter)
    }

    @OnClick(R.id.submit)
    fun submitAnswers() {
        loading.visibility = View.VISIBLE
        examingViewPageModel.submitAnswers(examsCode, adapter)
    }

    @OnClick(R.id.return_submit)
    fun returnSubmitPage() {
        questions_view_pager.currentItem = questions_view_pager.adapter!!.count
    }

    private fun chooseDot(position: Int) {
        if (questions_radio_group.getChildAt(position) == null) {
            return
        }
        val radioButton = questions_radio_group.getChildAt(position) as RadioButton
        radioButton.isChecked = true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode != KeyEvent.KEYCODE_BACK || event.action != KeyEvent.ACTION_DOWN) {
            return super.onKeyDown(keyCode, event)
        }
        if (examingViewPageModel.getType() == Type.RESULT) {
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
