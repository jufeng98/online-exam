package org.javamaster.fragmentlearning.service

import org.javamaster.fragmentlearning.data.model.Discussions
import org.javamaster.fragmentlearning.data.model.Page

/**
 * @author yudong
 * @date 2019/10/10
 */
interface DiscussService {

    fun createDiscussions(question: String, description: String, relevantTags: String): Int
    fun findDiscussionsList(question: String, sort: Int, page: Page): Pair<MutableList<Discussions>, Long>

}