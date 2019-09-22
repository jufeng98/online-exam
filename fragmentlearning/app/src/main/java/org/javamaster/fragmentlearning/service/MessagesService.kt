package org.javamaster.fragmentlearning.service

import org.javamaster.fragmentlearning.data.entity.Messages
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.data.model.ResultVo

/**
 * @author yudong
 * @date 2019/9/20
 */
interface MessagesService {
    fun hasUnreadMessages(): ResultVo<Boolean>
    fun findMessagesList(page: Page): ResultVo<List<Messages>>
    fun markMessages(id: Int?): ResultVo<Int>
}