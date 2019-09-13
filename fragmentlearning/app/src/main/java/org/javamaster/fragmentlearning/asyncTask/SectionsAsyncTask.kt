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
    private val operationListener: OperationListener<List<Sections>>
) : AsyncTask<String, Int, ResultVo<List<Sections>>>() {
    override fun doInBackground(vararg params: String): ResultVo<List<Sections>> {
        return learnService.findSectionsList(params[0])
    }

    override fun onPostExecute(result: ResultVo<List<Sections>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}