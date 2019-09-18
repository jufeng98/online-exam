package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService

/**
 * @author yudong
 * @date 2019/9/12
 */
class TopicsProgressAsyncTask(
    private val learnService: LearnService,
    private val operationListener: OperationListener<Map<String, Int>>
) : AsyncTask<Void, Int, ResultVo<Map<String, Int>>>() {
    override fun doInBackground(vararg params: Void?): ResultVo<Map<String, Int>> {
        return learnService.findTopicsProgress()
    }

    override fun onPostExecute(result: ResultVo<Map<String, Int>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}