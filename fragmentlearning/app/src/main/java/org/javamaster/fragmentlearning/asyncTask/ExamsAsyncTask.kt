package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.entity.Exams
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService

/**
 * @author yudong
 * @date 2019/9/17
 */
class ExamsAsyncTask(
    private val learnService: LearnService,
    private val operationListener: OperationListener<List<Exams>>
) : AsyncTask<Void, Int, ResultVo<List<Exams>>>() {
    override fun doInBackground(vararg params: Void?): ResultVo<List<Exams>> {
        return learnService.findExamsList()
    }

    override fun onPostExecute(result: ResultVo<List<Exams>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}