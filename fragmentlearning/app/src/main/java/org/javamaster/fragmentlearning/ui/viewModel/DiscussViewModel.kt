package org.javamaster.fragmentlearning.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.DiscussFormState

/**
 * @author yudong
 * @date 2019/10/10
 */
class DiscussViewModel : ViewModel() {

    private val _discussFormState = MutableLiveData<DiscussFormState>()
    val discussFormState: LiveData<DiscussFormState> = _discussFormState

    private val _dataValid = MutableLiveData<Boolean>()
    val dataValid: LiveData<Boolean> = _dataValid

    fun isDataValid(question: String, relevantTags: String) {
        discussDataChanged(question, relevantTags)
        _dataValid.value = _discussFormState.value!!.isDataValid
    }

    fun discussDataChanged(question: String, relevantTags: String) {
        var questionError: Int? = null
        var relevantTagsError: Int? = null
        var valid = true
        if (question == "") {
            questionError = R.string.question_require
            valid = false
        }
        if (relevantTags == "") {
            relevantTagsError = R.string.least_one_tag
            valid = false
        }
        _discussFormState.value = DiscussFormState(questionError, relevantTagsError, valid)
    }
}
