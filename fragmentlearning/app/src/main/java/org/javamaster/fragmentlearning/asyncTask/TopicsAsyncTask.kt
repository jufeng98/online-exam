package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.entity.Topics
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService

/**
 * @author yudong
 * @date 2019/9/12
 */
class TopicsAsyncTask(
    private val learnService: LearnService,
    private val operationListener: OperationListener<List<Topics>>
) : AsyncTask<Void, Int, ResultVo<List<Topics>>>() {
    override fun doInBackground(vararg params: Void?): ResultVo<List<Topics>> {
        return learnService.findTopicsList()
    }

    override fun onPostExecute(result: ResultVo<List<Topics>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}