package org.javamaster.b2c.core.service

import com.github.pagehelper.PageInfo
import org.javamaster.b2c.core.entity.Discussions
import org.javamaster.b2c.core.model.vo.CreateDiscussionsReqVo
import org.javamaster.b2c.core.model.vo.FindDiscussionsListReqVo
import org.springframework.security.core.userdetails.UserDetails

/**
 *
 * @author yudong
 * @date 2019/10/9
 */
interface DiscussionsService {
    /**
     * 创建讨论
     */
    fun createDiscussions(reqVo: CreateDiscussionsReqVo, userDetails: UserDetails): Int

    /**
     * 获取讨论列表
     */
    fun findDiscussionsList(reqVo: FindDiscussionsListReqVo, userDetails: UserDetails): PageInfo<Discussions>
}