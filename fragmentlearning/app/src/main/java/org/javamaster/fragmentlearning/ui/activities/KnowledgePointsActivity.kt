package org.javamaster.fragmentlearning.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.widget.*
import androidx.core.content.edit
import androidx.core.view.children
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_knowledge_points.*
import kotlinx.android.synthetic.main.activity_login.loading
import kotlinx.android.synthetic.main.activity_onboarding.view_pager
import kotlinx.android.synthetic.main.tool_bar_layout.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.asyncTask.KnowledgePointsAsyncTask
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.data.entity.KnowledgePoints
import org.javamaster.fragmentlearning.data.entity.Options
import org.javamaster.fragmentlearning.data.entity.Questions
import org.javamaster.fragmentlearning.enums.QuestionsTypeEnum
import org.javamaster.fragmentlearning.ioc.DaggerAppComponent
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService
import javax.inject.Inject
import kotlin.concurrent.thread


class KnowledgePointsActivity : BaseAppActivity() {

    @Inject
    lateinit var learnService: LearnService

    override fun initContentView(): Int? {
        return R.layout.activity_knowledge_points
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder().globalComponent(App.globalComponent).build().inject(this)
        val knowledgesCode = intent.getStringExtra("knowledgesCode")
        setSupportActionBar(app_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        KnowledgePointsAsyncTask(
            learnService,
            object : OperationListener<List<Triple<KnowledgePoints, Questions?, List<Options>?>>> {
                override fun success(t: List<Triple<KnowledgePoints, Questions?, List<Options>?>>) {
                    loading.visibility = View.GONE
                    initAdapter(t)

                }

                override fun fail(errorCode: Int, errorMsg: String) {
                    loading.visibility = View.GONE
                    super.fail(errorCode, errorMsg)
                }
            }).execute(knowledgesCode)
        loading.visibility = View.VISIBLE
    }

    fun initAdapter(list: List<Triple<KnowledgePoints, Questions?, List<Options>?>>) {
        val sumList = mutableListOf<Any>()
        list.forEach {
            sumList.add(it.first)
            if (it.second != null) {
                sumList.add(Pair(it.second!!, it.third))
            }
        }

        points_options_radio_group.removeAllViews()
        sumList.forEach { _ ->
            val radioButton = RadioButton(this)
            points_options_radio_group.addView(radioButton)
        }
        val radioButton = points_options_radio_group.getChildAt(0) as RadioButton
        radioButton.isChecked = true


        val adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return sumList.size
            }

            override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
                container.removeView(obj as View)
            }

            @Suppress("DEPRECATION")
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val item = sumList[position]
                val rootView: Any
                when (item) {
                    is KnowledgePoints -> {
                        rootView = layoutInflater.inflate(
                            R.layout.view_knowledge_points_item,
                            container,
                            false
                        ) as ViewGroup
                        val webView = rootView.findViewById<WebView>(R.id.content)
                        webView.loadDataWithBaseURL(null, item.knowledgePointsContent, "text/html", "utf-8", null)
                        // rootView.findViewById<TextView>(R.id.comments).setText()
                        rootView.findViewById<Button>(R.id.continue_next)
                            .setOnClickListener {
                                view_pager.currentItem = position + 1
                            }
                        thread {
                            try {
                                val resultVo = learnService.saveLearns(item.knowledgePointsCode)
                                Log.i(this::class.qualifiedName, resultVo.toString())
                                val resultVo1 = learnService.findTopicsProgress(false)
                                Log.i(this::class.qualifiedName, resultVo1.toString())
                                val topicsCode = App.getLearnSharedPreferences().getString("topicsCode", "")!!
                                val resultVo2 = learnService.findSectionsProgress(topicsCode, false)
                                Log.i(this::class.qualifiedName, resultVo2.toString())
                            } catch (e: Exception) {
                                Log.e(this::class.qualifiedName, "error", e)
                            }
                        }
                    }
                    is Pair<*, *> -> {
                        val questions: Questions = item.first as Questions
                        val optionsList: List<*> = item.second as List<*>
                        rootView = layoutInflater.inflate(
                            R.layout.view_knowledge_points_question_item,
                            container,
                            false
                        ) as ViewGroup
                        val webView =
                            rootView.findViewById<WebView>(R.id.questions_title)
                        val checkButton = rootView.findViewById<Button>(R.id.check_option)
                        webView.loadDataWithBaseURL(null, questions.questionsTitle, "text/html", "utf-8", null)
                        val linearLayout: LinearLayout = rootView.findViewById(R.id.question_options)
                        if (
                            questions.questionsType.toInt() == QuestionsTypeEnum.SINGLE.code
                            || questions.questionsType.toInt() == QuestionsTypeEnum.JUDGE.code
                        ) {
                            val radioGroup = RadioGroup(this@KnowledgePointsActivity)
                            optionsList.forEach {
                                val button = RadioButton(this@KnowledgePointsActivity)
                                button.setTextColor(resources.getColor(R.color.colorAccent))
                                val options = it as Options
                                button.text = options.optionName
                                button.tag = options.correct
                                radioGroup.addView(button)
                            }
                            checkButton.setOnClickListener {
                                var answerRight = false
                                radioGroup.children.forEach {
                                    val button = it as RadioButton
                                    if (button.isChecked) {
                                        answerRight = button.tag as Boolean
                                    }
                                }
                                showResponse(answerRight, checkButton)
                            }
                            linearLayout.addView(radioGroup)
                        } else if (questions.questionsType.toInt() == QuestionsTypeEnum.MULTIPLY.code) {
                            optionsList.forEach {
                                val checkBox = CheckBox(this@KnowledgePointsActivity)
                                checkBox.setTextColor(resources.getColor(R.color.colorAccent))
                                val options = it as Options
                                checkBox.text = options.optionName
                                checkBox.tag = options.correct
                                linearLayout.addView(checkBox)
                            }
                            checkButton.setOnClickListener {
                                var selectedNum = 0
                                val correctOptions = mutableListOf<CheckBox>()
                                linearLayout.children.forEach {
                                    val checkBox = it as CheckBox
                                    val correct = checkBox.tag as Boolean
                                    if (correct) {
                                        correctOptions.add(checkBox)
                                    }
                                    if (checkBox.isChecked) {
                                        selectedNum++
                                    }
                                }
                                var answerRight = true
                                if (correctOptions.size == selectedNum) {
                                    for (correctOption in correctOptions) {
                                        if (!correctOption.isChecked) {
                                            answerRight = false
                                            break
                                        }
                                    }
                                } else {
                                    answerRight = false
                                }
                                showResponse(answerRight, checkButton)
                            }
                        }
                    }
                    else -> throw RuntimeException()
                }
                container.addView(rootView)
                return rootView
            }
        }
        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                chooseDot(position)
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    fun chooseDot(position: Int) {
        if (points_options_radio_group.getChildAt(position) == null) {
            return
        }
        val radioButton = points_options_radio_group.getChildAt(position) as RadioButton
        radioButton.isChecked = true
    }

    fun showResponse(answerRight: Boolean, checkButton: Button) {
        val text = if (answerRight) {
            getString(R.string.answer_right_tip)
        } else {
            getString(R.string.answer_wrong_tip)
        }
        Snackbar.make(checkButton, text, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        app_tool_bar.inflateMenu(R.menu.menu_knowledge_points)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                super.onKeyDown(KeyEvent.KEYCODE_BACK, KeyEvent(KeyEvent.KEYCODE_BACK, 0))
                finish()
            }
        }
        return true
    }

    companion object {
        fun actionStart(context: Context, knowledgesCode: String) {
            val intent = Intent(context, KnowledgePointsActivity::class.java)
            intent.putExtra("knowledgesCode", knowledgesCode)
            App.getLearnSharedPreferences().edit {
                putString("knowledgesCode", knowledgesCode)
                apply()
            }
            context.startActivity(intent)
        }
    }
}
