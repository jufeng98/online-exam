package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.entity.KnowledgePoints
import org.javamaster.fragmentlearning.data.entity.Options
import org.javamaster.fragmentlearning.data.entity.Questions
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.LearnService

/**
 * @author yudong
 * @date 2019/9/13
 */
class KnowledgePointsAsyncTask(
    private val learnService: LearnService,
    private val operationListener: OperationListener<List<Triple<KnowledgePoints, Questions?, List<Options>?>>>
) : AsyncTask<String, Int, ResultVo<List<Triple<KnowledgePoints, Questions?, List<Options>?>>>>() {
    override fun doInBackground(vararg params: String): ResultVo<List<Triple<KnowledgePoints, Questions?, List<Options>?>>> {
        val resultVo = learnService.findKnowledgePointsList(params[0])
        if (!resultVo.success) {
            return ResultVo(success = false, errorCode = resultVo.errorCode, errorMsg = resultVo.errorMsg)
        }
        val list = resultVo.data!!.map {
            if (it.questionsCode == "") {
                return@map Triple(it, null, null)
            }
            val resultVo1 = learnService.findQuestionsList(it.questionsCode)
            if (!resultVo.success) {
                return@map Triple(it, null, null)
            }
            val resultVo2 = learnService.findAssociateOptions(it.questionsCode)
            if (!resultVo2.success) {
                return@map Triple(it, null, null)
            }
            return@map Triple(it, resultVo1.data!![0], resultVo2.data)
        }
        return ResultVo(success = true, data = list)
    }

    override fun onPostExecute(result: ResultVo<List<Triple<KnowledgePoints, Questions?, List<Options>?>>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}