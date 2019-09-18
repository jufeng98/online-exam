package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.entity.ExamQuestionsVo
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService

/**
 * @author yudong
 * @date 2019/9/17
 */
class QuestionsAsyncTask(
    private val learnService: LearnService,
    private val operationListener: OperationListener<List<ExamQuestionsVo>>
) : AsyncTask<String, Int, ResultVo<List<ExamQuestionsVo>>>() {
    override fun doInBackground(vararg params: String?): ResultVo<List<ExamQuestionsVo>> {
        return learnService.findQuestionsByExamsCode(params[0]!!)
    }

    override fun onPostExecute(result: ResultVo<List<ExamQuestionsVo>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}