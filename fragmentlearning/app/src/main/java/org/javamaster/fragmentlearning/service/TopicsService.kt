package org.javamaster.fragmentlearning.data

import org.javamaster.fragmentlearning.data.model.ResultVo
import org.javamaster.fragmentlearning.data.model.Topics

/**
 * @author yudong
 * @date 2019/8/18
 */
interface TopicsService {

    fun findTopicsList(): ResultVo<List<Topics>>
}