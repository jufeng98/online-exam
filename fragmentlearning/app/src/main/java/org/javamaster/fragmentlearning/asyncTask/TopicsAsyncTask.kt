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
    private val operationListener: OperationListener<Pair<List<Topics>, Map<String, Int>>>
) : AsyncTask<Void, Int, ResultVo<Pair<List<Topics>, Map<String, Int>>>>() {
    override fun doInBackground(vararg params: Void?): ResultVo<Pair<List<Topics>, Map<String, Int>>> {
        val resultVo = learnService.findTopicsList()
        val resultVo1 = learnService.findTopicsProgress()
        return ResultVo(true, null, null, Pair(resultVo.data!!, resultVo1.data!!), null)
    }

    override fun onPostExecute(result: ResultVo<Pair<List<Topics>, Map<String, Int>>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}