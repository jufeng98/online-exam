package org.javamaster.fragmentlearning.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.javamaster.fragmentlearning.adapter.ExamsQuestionsAdapter
import org.javamaster.fragmentlearning.adapter.Type
import org.javamaster.fragmentlearning.consts.AppConsts
import org.javamaster.fragmentlearning.data.ActivityExamingState
import org.javamaster.fragmentlearning.data.model.AnswerResultState
import org.javamaster.fragmentlearning.service.LearnService
import javax.inject.Inject
import kotlin.concurrent.thread

/**
 * @author yudong
 * @date 2019/9/20
 */
class ExamingViewPageModel @Inject constructor(private val learnService: LearnService) : ViewModel() {

    private var allQuestionsComplete = false
    private var type = Type.SUBMIT

    private val _activityExamingState = MutableLiveData<ActivityExamingState>()
    val activityExamingState: LiveData<ActivityExamingState> = _activityExamingState

    private val _answerResultState = MutableLiveData<AnswerResultState>()
    val answerResultState: LiveData<AnswerResultState> = _answerResultState

    fun changePage(position: Int, adapter: ExamsQuestionsAdapter) {
        val activityExamingState = ActivityExamingState()
        activityExamingState.position = position
        activityExamingState.type = type
        if (position == adapter.getItemCount()) {
            allQuestionsComplete = true
            activityExamingState.showReturnSubmitButton = false
            activityExamingState.showSubmitButton = true
            activityExamingState.showQuestionsDot = false
            activityExamingState.showNextButton = false
            activityExamingState.constructSubmitPage = true
        } else {
            activityExamingState.showReturnSubmitButton = allQuestionsComplete
            activityExamingState.showSubmitButton = false
            activityExamingState.showQuestionsDot = true
            activityExamingState.showNextButton = true
            activityExamingState.constructSubmitPage = false
        }
        if (type == Type.RESULT) {
            activityExamingState.showQuestionsDot = false
            activityExamingState.showSubmitButton = false
        }
        activityExamingState.allQuestionsComplete = allQuestionsComplete
        _activityExamingState.value = activityExamingState
    }

    fun submitAnswers(examsCode: String, adapter: ExamsQuestionsAdapter) {
        thread {
            val answerResultState = AnswerResultState()
            val examsAnswers = adapter.getAnswers()
            val resultVo = learnService.submitAnswers(examsCode, examsAnswers)
            if (!resultVo.success) {
                answerResultState.showError = resultVo.errorCode != AppConsts.LOGIN_ERROR_CODE
                answerResultState.errorMsg = resultVo.errorMsg
                _answerResultState.postValue(answerResultState)
            } else {
                type = Type.RESULT
                answerResultState.submitAnswersResVo = resultVo.data!!
                answerResultState.type = type
                _answerResultState.postValue(answerResultState)
            }
        }

    }

    fun getType(): Type {
        return type
    }
}