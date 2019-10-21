package org.javamaster.fragmentlearning.adapter

import android.text.Html
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.entity.ExamQuestionsVo
import org.javamaster.fragmentlearning.data.model.ExamsAnswer
import org.javamaster.fragmentlearning.data.model.SubmitAnswersResVo
import org.javamaster.fragmentlearning.enums.QuestionsTypeEnum
import org.javamaster.fragmentlearning.ui.activities.ExamingActivity


/**
 * @author yudong
 * @date 2019/9/17
 */
class ExamsQuestionsAdapter(val list: List<ExamQuestionsVo>, val context: ExamingActivity) : PagerAdapter() {
    private val questionsViewMap = mutableMapOf<Int, View?>()
    private val answerChoose = mutableMapOf<Int, Triple<Boolean, String, Set<Int>>>()
    private lateinit var submitAnswersResVo: SubmitAnswersResVo
    private var type = Type.SUBMIT

    fun changeType(type: Type) {
        this.type = type
    }

    fun initSubmitAnswersResVo(submitAnswersResVo: SubmitAnswersResVo) {
        this.submitAnswersResVo = submitAnswersResVo
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size + 1
    }

    fun getItemCount(): Int {
        return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        if (position == getItemCount()) {
            return
        }
        if (type == Type.RESULT) {
            container.removeView(obj as View)
        }
    }

    @Suppress("DEPRECATION")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (type == Type.RESULT) {
            return instantiateQuestionAnswerItem(container, position)
        }

        var rootView = questionsViewMap[position]
        if (rootView != null) {
            return rootView
        }
        rootView = context.layoutInflater.inflate(
            R.layout.view_exams_question_item,
            container,
            false
        ) as ViewGroup
        if (position == getItemCount()) {
            container.addView(rootView)
            questionsViewMap[position] = rootView
            return rootView
        }
        val questionsVo = list[position]
        val questionsTitle: TextView = rootView.findViewById(R.id.questions_title)
        questionsTitle.text = Html.fromHtml(questionsVo.questionsTitle)
        val linearLayout: LinearLayout = rootView.findViewById(R.id.question_options)
        if (
            questionsVo.questionsType.toInt() == QuestionsTypeEnum.SINGLE.code
            || questionsVo.questionsType.toInt() == QuestionsTypeEnum.JUDGE.code
        ) {
            val radioGroup = RadioGroup(context)
            questionsVo.optionsVos.forEach {
                val radioButton = RadioButton(context)
                radioButton.setTextColor(context.resources.getColor(R.color.colorAccent))
                radioButton.text = it.optionName
                radioButton.id = it.optionId
                radioGroup.addView(radioButton)
            }
            radioGroup.setOnCheckedChangeListener { _, itemId ->
                singleOrJudgeChooseAnswer(position, questionsVo.questionsCode, itemId)
            }
            linearLayout.addView(radioGroup)
        } else if (questionsVo.questionsType.toInt() == QuestionsTypeEnum.MULTIPLY.code) {
            questionsVo.optionsVos.forEach {
                val checkBox = CheckBox(context)
                checkBox.setTextColor(context.resources.getColor(R.color.colorAccent))
                checkBox.text = it.optionName
                checkBox.id = it.optionId
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    val answered = linearLayout.children.any { childCheckBox ->
                        val box = childCheckBox as CheckBox
                        box.isChecked
                    }
                    multiChooseAnswer(position, questionsVo.questionsCode, buttonView.id, answered, isChecked)
                }
                linearLayout.addView(checkBox)
            }
        }
        container.addView(rootView)
        questionsViewMap[position] = rootView
        return rootView
    }

    @Suppress("DEPRECATION")
    fun instantiateQuestionAnswerItem(container: ViewGroup, position: Int): Any {
        if (position != getItemCount()) {
            container.removeView(questionsViewMap[position])
        }
        if (position == getItemCount()) {
            return questionsViewMap[position]!!
        }
        val rootView = context.layoutInflater.inflate(
            R.layout.view_exams_question_item,
            container,
            false
        ) as ViewGroup
        fillRootView(rootView, position, false)
        container.addView(rootView)
        return rootView
    }

    @Suppress("DEPRECATION")
    fun fillRootView(rootView: View, position: Int, clear: Boolean) {
        val questionsVo = list[position]
        val answersVo = submitAnswersResVo.answerDetails[position]
        val questionsTitle: TextView = rootView.findViewById(R.id.questions_title)
        questionsTitle.text = Html.fromHtml(questionsVo.questionsTitle)
        val linearLayout: LinearLayout = rootView.findViewById(R.id.question_options)
        if (clear) {
            linearLayout.removeAllViews()
        }
        if (
            questionsVo.questionsType.toInt() == QuestionsTypeEnum.SINGLE.code
            || questionsVo.questionsType.toInt() == QuestionsTypeEnum.JUDGE.code
        ) {
            val radioGroup = RadioGroup(context)
            questionsVo.optionsVos.forEach {
                val radioButton = RadioButton(context)
                radioButton.setTextColor(context.resources.getColor(R.color.colorAccent))
                radioButton.text = it.optionName
                radioButton.isClickable = false
                if (answersVo.examsRightAnswers.contains(it.optionId)) {
                    radioButton.isChecked = true
                }
                radioGroup.addView(radioButton)
            }
            linearLayout.addView(radioGroup)
        } else if (questionsVo.questionsType.toInt() == QuestionsTypeEnum.MULTIPLY.code) {
            questionsVo.optionsVos.forEach {
                val checkBox = CheckBox(context)
                checkBox.setTextColor(context.resources.getColor(R.color.colorAccent))
                checkBox.text = it.optionName
                checkBox.isClickable = false
                if (answersVo.examsRightAnswers.contains(it.optionId)) {
                    checkBox.isChecked = true
                }
                linearLayout.addView(checkBox)
            }
        } else {
            throw RuntimeException(context.getString(R.string.unknown_question_type))
        }
        val answerAnalysis = TextView(context)
        answerAnalysis.text =
            Html.fromHtml(context.getString(R.string.answer_analysis).format(answersVo.answerAnalysis))
        answerAnalysis.setTextColor(context.resources.getColor(R.color.colorAccent))
        linearLayout.addView(answerAnalysis)
    }

    fun constructSubmitOrResultPage() {
        val rootView: View = questionsViewMap[getItemCount()]!!
        if (type == Type.SUBMIT) {
            rootView.findViewById<TextView>(R.id.questions_title).text = context.getString(R.string.before_submit_tip)
        } else {
            val text = context.getString(R.string.exams_score_tip)
            rootView.findViewById<TextView>(R.id.questions_title).text = text.format(submitAnswersResVo.score)
        }
        val linearLayout: LinearLayout = rootView.findViewById(R.id.question_options)
        linearLayout.visibility = View.GONE
        val gridLayout: GridLayout = rootView.findViewById(R.id.question_options_grid)
        gridLayout.removeAllViews()
        gridLayout.visibility = View.VISIBLE
        if (type == Type.SUBMIT) {
            questionsViewMap.entries.forEachIndexed { index, mutableEntry ->
                if (index == count - 1) {
                    return@forEachIndexed
                }
                val button = newButton()
                button.text = (mutableEntry.key + 1).toString()
                button.setOnClickListener {
                    context.findViewById<ViewPager>(R.id.questions_view_pager).currentItem = mutableEntry.key
                }
                val choose = answerChoose[mutableEntry.key]?.first ?: false
                if (choose) {
                    button.background = context.getDrawable(R.drawable.button_circle_shape_choose)
                } else {
                    button.background = context.getDrawable(R.drawable.button_circle_shape)
                }
                gridLayout.addView(button)
            }
        } else {
            submitAnswersResVo.answerDetails.forEach { answerDetail ->
                val button = newButton()
                button.text = (answerDetail.questionsNum + 1).toString()
                button.setOnClickListener {
                    context.findViewById<ViewPager>(R.id.questions_view_pager).currentItem = answerDetail.questionsNum
                }
                if (answerDetail.correct) {
                    button.background = context.getDrawable(R.drawable.button_circle_shape_choose)
                } else {
                    button.background = context.getDrawable(R.drawable.button_circle_shape_wrong)
                }
                gridLayout.addView(button)
            }
        }
    }

    private fun newButton(): Button {
        val button = Button(context)
        val gridLayoutParams =
            GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            )
        button.layoutParams = gridLayoutParams
        gridLayoutParams.topMargin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.resources.displayMetrics).toInt()
        gridLayoutParams.height =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, context.resources.displayMetrics).toInt()
        gridLayoutParams.width =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, context.resources.displayMetrics).toInt()
        button.layoutParams = gridLayoutParams
        button.background = context.getDrawable(R.drawable.button_circle_shape)
        return button
    }

    private fun singleOrJudgeChooseAnswer(position: Int, questionsCode: String, itemId: Int) {
        val answersSet = linkedSetOf(itemId)
        answerChoose[position] = Triple(true, questionsCode, answersSet)
        changeDot(position, true)
    }

    private fun multiChooseAnswer(
        position: Int,
        questionsCode: String,
        itemId: Int,
        answered: Boolean,
        isChecked: Boolean
    ) {
        if (answerChoose[position] == null) {
            answerChoose[position] = Triple(answered, questionsCode, linkedSetOf())
        }
        val answersSet = answerChoose[position]!!.third as LinkedHashSet<Int>
        if (isChecked) {
            answersSet.add(itemId)
        } else {
            answersSet.remove(itemId)
        }
        changeDot(position, answered)
    }

    @Suppress("DEPRECATION")
    fun changeDot(position: Int, answered: Boolean) {
        val radioGroup = context.findViewById<RadioGroup>(R.id.questions_radio_group)
        if (answered) {
            radioGroup.getChildAt(position)
                .setBackgroundColor(context.resources.getColor(R.color.colorAppPrimary))
        } else {
            radioGroup.getChildAt(position)
                .setBackgroundColor(context.resources.getColor(R.color.colorTransparent))
        }
    }

    fun getAnswers(): List<ExamsAnswer> {
        val examsAnswers: MutableList<ExamsAnswer> = mutableListOf()
        for (i in 0 until getItemCount()) {
            val it = answerChoose[i]
            if (it != null) {
                examsAnswers.add(ExamsAnswer(i, it.second, it.third))
            } else {
                examsAnswers.add(ExamsAnswer(i, list[i].questionsCode, mutableSetOf()))
            }
        }
        return examsAnswers
    }
}

enum class Type {
    SUBMIT,
    RESULT
}