package org.javamaster.fragmentlearning.adapter

import android.app.Activity
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
import org.javamaster.fragmentlearning.enums.QuestionsTypeEnum


/**
 * @author yudong
 * @date 2019/9/17
 */
class ExamsQuestionsAdapter(val list: List<ExamQuestionsVo>, val context: Activity) : PagerAdapter() {
    private val questionsMap = mutableMapOf<Int, View?>()
    private val answerChoose = mutableMapOf<Int, Triple<Boolean, String, Set<Int>>>()

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
        // container.removeView(obj as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var rootView = questionsMap[position]
        if (rootView != null) {
            if (position >= list.size) {
                constructSubmitPage(position)
                return rootView
            }
            return rootView
        }
        rootView = context.layoutInflater.inflate(
            R.layout.view_exams_question_item,
            container,
            false
        ) as ViewGroup
        if (position >= list.size) {
            constructSubmitPage(position)
            container.addView(rootView)
            questionsMap[position] = rootView
            return rootView
        }
        if (position >= list.size) {
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
        questionsMap[position] = rootView
        return rootView
    }

    fun constructSubmitPage(position: Int) {
        if (questionsMap[position] == null) {
            return
        }
        val rootView: View = questionsMap[position]!!
        rootView.findViewById<TextView>(R.id.questions_title).text = context.getString(R.string.before_submit_tip)
        val linearLayout: LinearLayout = rootView.findViewById(R.id.question_options)
        linearLayout.visibility = View.GONE
        val gridLayout: GridLayout = rootView.findViewById(R.id.question_options_grid)
        gridLayout.removeAllViews()
        gridLayout.visibility = View.VISIBLE
        questionsMap.entries.forEachIndexed { index, mutableEntry ->
            if (index == count - 1) {
                return@forEachIndexed
            }
            val button = Button(context)
            button.text = (mutableEntry.key + 1).toString()
            button.setOnClickListener {
                context.findViewById<ViewPager>(R.id.questions_view_pager).currentItem = mutableEntry.key
            }
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
            val choose = answerChoose[mutableEntry.key]?.first ?: false
            if (choose) {
                button.background = context.getDrawable(R.drawable.button_circle_shape_choose)
            } else {
                button.background = context.getDrawable(R.drawable.button_circle_shape)
            }
            gridLayout.addView(button)
        }
    }

    private fun singleOrJudgeChooseAnswer(position: Int, questionsCode: String, itemId: Int) {
        val answersSet = mutableSetOf(itemId)
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
            answerChoose[position] = Triple(answered, questionsCode, mutableSetOf())
        }
        var answersSet = answerChoose[position]!!.third as MutableSet<Int>
        if (isChecked) {
            answersSet.add(itemId)
        } else {
            answersSet.remove(itemId)
        }
        changeDot(position, answered)
    }

    @Suppress("DEPRECATION")
    fun changeDot(position: Int, answered: Boolean) {
        val radioGroup = context.findViewById<RadioGroup>(R.id.options_radio_group)
        if (answered) {
            radioGroup.getChildAt(position)
                .setBackgroundColor(context.resources.getColor(R.color.colorAppPrimary))
        } else {
            radioGroup.getChildAt(position)
                .setBackgroundColor(context.resources.getColor(R.color.colorTransparent))
        }
    }

    fun getAnswers(): List<Pair<String, Set<Int>>> {
        return answerChoose.values.map {
            Pair(it.second, it.third)
        }
    }
}