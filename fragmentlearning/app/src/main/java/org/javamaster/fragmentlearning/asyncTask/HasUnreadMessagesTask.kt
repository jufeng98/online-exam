package org.javamaster.fragmentlearning.asyncTask

import android.os.AsyncTask
import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.MessagesService

/**
 * @author yudong
 * @date 2019/9/20
 */
class HasUnreadMessagesTask(
    private val messagesService: MessagesService,
    private val operationListener: OperationListener<Boolean>
) : AsyncTask<Void, Int, ResultVo<Boolean>>() {
    override fun doInBackground(vararg params: Void?): ResultVo<Boolean> {
        return messagesService.hasUnreadMessages()
    }

    override fun onPostExecute(result: ResultVo<Boolean>) {
        if (!result.success) {
            operationListener.fail(result.errorCode!!, result.errorMsg!!)
        } else {
            operationListener.success(result.data!!)
        }
    }
}