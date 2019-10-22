package org.javamaster.fragmentlearning.service

import org.javamaster.fragmentlearning.data.entity.Messages
import org.javamaster.fragmentlearning.data.model.Page
import org.javamaster.fragmentlearning.data.model.ResultVo

/**
 * @author yudong
 * @date 2019/9/20
 */
interface MessagesService {
    fun findMessagesList(page: Page): Pair<MutableList<Messages>, Long>
    fun markMessages(id: Int?): ResultVo<Int>
}