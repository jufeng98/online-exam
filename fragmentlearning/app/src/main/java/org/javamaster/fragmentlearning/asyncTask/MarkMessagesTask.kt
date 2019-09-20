package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.MessagesService

/**
 * @author yudong
 * @date 2019/9/20
 */
class MarkMessagesTask(
    private val messagesService: MessagesService,
    private val operationListener: OperationListener<Int>
) : AsyncTask<Int?, Int, ResultVo<Int>>() {
    override fun doInBackground(vararg params: Int?): ResultVo<Int> {
        return messagesService.markMessages(params[0])
    }

    override fun onPostExecute(result: ResultVo<Int>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}