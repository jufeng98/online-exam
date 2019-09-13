package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.entity.Knowledges
import org.javamaster.fragmentlearning.data.entity.KnowledgesQuestionNumVo
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService

/**
 * @author yudong
 * @date 2019/9/13
 */
class KnowledgesAsyncTask(
    private val learnService: LearnService,
    private val operationListener: OperationListener<Pair<List<Knowledges>, List<KnowledgesQuestionNumVo>>>
) : AsyncTask<String, Int, ResultVo<Pair<List<Knowledges>, List<KnowledgesQuestionNumVo>>>>() {
    override fun doInBackground(vararg params: String): ResultVo<Pair<List<Knowledges>, List<KnowledgesQuestionNumVo>>> {
        val resultVo = learnService.findKnowledgesList(params[0])
        val resultVo1 = learnService.findKnowledgesQuestionNum(params[0])
        if (!resultVo.success) {
            return ResultVo(success = false, errorCode = resultVo.errorCode, errorMsg = resultVo.errorMsg)
        }
        if (!resultVo1.success) {
            return ResultVo(success = false, errorCode = resultVo1.errorCode, errorMsg = resultVo1.errorMsg)
        }
        return ResultVo(success = true, data = Pair(resultVo.data!!, resultVo1.data!!))
    }

    override fun onPostExecute(result: ResultVo<Pair<List<Knowledges>, List<KnowledgesQuestionNumVo>>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}