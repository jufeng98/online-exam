package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.entity.Sections
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService

/**
 * @author yudong
 * @date 2019/9/12
 */
class SectionsAsyncTask(
    private val learnService: LearnService,
    private val operationListener: OperationListener<Pair<List<Sections>, Map<String, Int>>>
) : AsyncTask<String, Int, ResultVo<Pair<List<Sections>, Map<String, Int>>>>() {
    override fun doInBackground(vararg params: String): ResultVo<Pair<List<Sections>, Map<String, Int>>> {
        val resultVo = learnService.findSectionsList(params[0])
        val resultVo1 = learnService.findSectionsProgress()
        return ResultVo(true, null, null, Pair(resultVo.data!!, resultVo1.data!!), null)
    }

    override fun onPostExecute(result: ResultVo<Pair<List<Sections>, Map<String, Int>>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}