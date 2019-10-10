package org.javamaster.b2c.core.service.impl

import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import org.javamaster.b2c.core.entity.Discussions
import org.javamaster.b2c.core.entity.DiscussionsExample
import org.javamaster.b2c.core.mapper.DiscussionsMapper
import org.javamaster.b2c.core.model.vo.CreateDiscussionsReqVo
import org.javamaster.b2c.core.model.vo.FindDiscussionsListReqVo
import org.javamaster.b2c.core.service.DiscussionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class DiscussionsServiceImpl : DiscussionsService {

    @Autowired
    private lateinit var discussionsMapper: DiscussionsMapper

    override fun createDiscussions(reqVo: CreateDiscussionsReqVo, userDetails: UserDetails): Int {
        val discussions = Discussions()
        discussions.username = userDetails.username
        discussions.question = reqVo.question
        discussions.description = reqVo.description
        discussions.relevantTags = reqVo.relevantTags.joinToString(";")
        return discussionsMapper.insertSelective(discussions)
    }

    override fun findDiscussionsList(reqVo: FindDiscussionsListReqVo, userDetails: UserDetails): PageInfo<Discussions> {
        PageHelper.startPage<Discussions>(reqVo.page.pageNum, reqVo.page.pageSize, "create_time desc")
        val discussionsExample = DiscussionsExample()
        if (reqVo.question != "") {
            discussionsExample.createCriteria().andQuestionLike("%${reqVo.question}%")
        }
        return PageInfo(discussionsMapper.selectByExample(discussionsExample))
    }

}