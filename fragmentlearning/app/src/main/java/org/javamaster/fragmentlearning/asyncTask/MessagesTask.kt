package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.entity.Messages
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.MessagesService

/**
 * @author yudong
 * @date 2019/9/20
 */
class MessagesTask(
    private val messagesService: MessagesService,
    private val operationListener: OperationListener<Pair<List<Messages>, Long>>
) : AsyncTask<Page, Int, ResultVo<List<Messages>>>() {
    override fun doInBackground(vararg params: Page?): ResultVo<List<Messages>> {
        return messagesService.findMessagesList(params[0]!!)
    }

    override fun onPostExecute(result: ResultVo<List<Messages>>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(Pair(result.data!!, result.total!!))
        }
    }
}